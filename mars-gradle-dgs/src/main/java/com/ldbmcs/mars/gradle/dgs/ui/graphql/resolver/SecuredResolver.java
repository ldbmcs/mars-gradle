package com.ldbmcs.mars.gradle.dgs.ui.graphql.resolver;

import com.ldbmcs.mars.gradle.dgs.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.dgs.infra.context.CurrentContext;

public interface SecuredResolver extends SystemGraphQLResolver {
    default User getCurrentUser() {
        return CurrentContext.user();
    }
}
