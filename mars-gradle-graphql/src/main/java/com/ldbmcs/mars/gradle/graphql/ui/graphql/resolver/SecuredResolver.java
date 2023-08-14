package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver;

import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.graphql.infra.context.CurrentContext;

public interface SecuredResolver extends SystemGraphQLResolver<Void> {
    default User getCurrentUser() {
        return CurrentContext.user();
    }
}
