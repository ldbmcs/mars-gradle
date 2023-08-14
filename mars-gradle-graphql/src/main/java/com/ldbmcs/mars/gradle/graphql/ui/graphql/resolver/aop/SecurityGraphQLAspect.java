package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.aop;

import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.graphql.core.shared.Errors;
import com.ldbmcs.mars.gradle.graphql.infra.context.CurrentContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityGraphQLAspect {

    @Before("isSecuredResolverMethod()")
    public void doSecurityCheck() {
        User currentUser = CurrentContext.user();
        if (currentUser == null) {
            throw Errors.unauthorized();
        }
    }

    @Pointcut("target(com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.SecuredResolver) && execution(public * *(..))")
    private void isSecuredResolverMethod() {
    }
}
