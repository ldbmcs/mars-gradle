package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.mutation.user;

import com.ldbmcs.mars.gradle.graphql.common.utils.BCryptUtil;
import com.ldbmcs.mars.gradle.graphql.core.domain.auth.service.AuthTokenDomainService;
import com.ldbmcs.mars.gradle.graphql.core.domain.auth.service.IdentityDomainService;
import com.ldbmcs.mars.gradle.graphql.core.domain.shared.models.YesOrNo;
import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.graphql.core.domain.user.repository.UserMapper;
import com.ldbmcs.mars.gradle.graphql.core.shared.Errors;
import com.ldbmcs.mars.gradle.graphql.infra.context.CurrentContext;
import com.ldbmcs.mars.gradle.graphql.infra.redis.RedisTemplateHelper;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.type.user.SignInUserInput;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.type.user.SignInUserPayload;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.type.user.SignUpUserInput;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.type.user.SignUpUserPayload;
import com.ldbmcs.mars.gradle.graphql.core.app.service.user.UserAppService;
import com.ldbmcs.mars.gradle.graphql.core.domain.auth.models.Identity;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.BaseAnonymousMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Optional;

@Component
public class UserAnonymousMutationResolver extends BaseAnonymousMutationResolver {
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

    public SignInUserPayload signInUser(@Valid SignInUserInput input, DataFetchingEnvironment environment) {
        Identity identity = Optional.ofNullable(identityDomainService.selectByPrincipal(input.getPrincipal()))
                .orElseThrow(() -> Errors.create(Errors.ERROR_NOT_REGISTERED));
        boolean matched = BCryptUtil.checkpw(input.getCredential(), identity.getCredential());
        YesOrNo.fromBoolean(matched).ifNoThrow(() -> Errors.create(Errors.ERROR_INVALID_CREDENTIALS));
        User user = Optional.ofNullable(userMapper.selectById(identity.getUserId()))
                .orElseThrow(() -> Errors.create(Errors.ERROR_INVALID_CREDENTIALS));
        String token = authTokenService.generate(user.getId());
        return new SignInUserPayload(user, token);
    }

    public SignUpUserPayload signUpUser(@Valid SignUpUserInput input, DataFetchingEnvironment environment) {
        User user = userAppService.signUp(input.getMobile(), input.getPassword(), input.getName());
        return new SignUpUserPayload(user);
    }

    public Boolean logout() {
        String key = String.format(AuthTokenDomainService.SESSION_ID, CurrentContext.authToken());
        redisTemplateHelper.deleteKey(key);
        return true;
    }
}
