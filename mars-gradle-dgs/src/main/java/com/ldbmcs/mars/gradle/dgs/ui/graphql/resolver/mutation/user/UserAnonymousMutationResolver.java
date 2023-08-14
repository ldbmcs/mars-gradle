package com.ldbmcs.mars.gradle.dgs.ui.graphql.resolver.mutation.user;

import com.ldbmcs.mars.gradle.dgs.common.utils.BCryptUtil;
import com.ldbmcs.mars.gradle.dgs.core.app.service.user.UserAppService;
import com.ldbmcs.mars.gradle.dgs.core.domain.auth.models.Identity;
import com.ldbmcs.mars.gradle.dgs.core.domain.auth.service.AuthTokenDomainService;
import com.ldbmcs.mars.gradle.dgs.core.domain.auth.service.IdentityDomainService;
import com.ldbmcs.mars.gradle.dgs.core.domain.shared.models.YesOrNo;
import com.ldbmcs.mars.gradle.dgs.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.dgs.core.domain.user.repository.UserMapper;
import com.ldbmcs.mars.gradle.dgs.core.shared.Errors;
import com.ldbmcs.mars.gradle.dgs.infra.context.CurrentContext;
import com.ldbmcs.mars.gradle.dgs.infra.redis.RedisTemplateHelper;
import com.ldbmcs.mars.gradle.dgs.ui.graphql.resolver.AnonymousResolver;
import com.ldbmcs.mars.gradle.dgs.ui.graphql.type.user.SignInUserInput;
import com.ldbmcs.mars.gradle.dgs.ui.graphql.type.user.SignInUserPayload;
import com.ldbmcs.mars.gradle.dgs.ui.graphql.type.user.SignUpUserInput;
import com.ldbmcs.mars.gradle.dgs.ui.graphql.type.user.SignUpUserPayload;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.ldbmcs.mars.gradle.dgs.core.domain.auth.service.AuthTokenDomainService.SESSION_ID;

@DgsComponent
public class UserAnonymousMutationResolver implements AnonymousResolver {
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private AuthTokenDomainService authTokenService;
    @Autowired
    private IdentityDomainService identityDomainService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @DgsMutation
    public SignInUserPayload signInUser(@InputArgument SignInUserInput input, DataFetchingEnvironment environment) {
        Identity identity = Optional.ofNullable(identityDomainService.selectByPrincipal(input.getPrincipal()))
                .orElseThrow(() -> Errors.create(Errors.ERROR_NOT_REGISTERED));
        boolean matched = BCryptUtil.checkpw(input.getCredential(), identity.getCredential());
        YesOrNo.fromBoolean(matched).ifNoThrow(() -> Errors.create(Errors.ERROR_INVALID_CREDENTIALS));
        User user = Optional.ofNullable(userMapper.selectById(identity.getUserId()))
                .orElseThrow(() -> Errors.create(Errors.ERROR_INVALID_CREDENTIALS));
        String token = authTokenService.generate(user.getId());
        return new SignInUserPayload(user, token);
    }

    @DgsMutation
    public SignUpUserPayload signUpUser(@InputArgument SignUpUserInput input, DataFetchingEnvironment environment) {
        User user = userAppService.signUp(input.getMobile(), input.getPassword(), input.getName());
        return new SignUpUserPayload(user);
    }

    @DgsMutation
    public Boolean logout() {
        String key = String.format(SESSION_ID, CurrentContext.authToken());
        redisTemplateHelper.deleteKey(key);
        return true;
    }
}
