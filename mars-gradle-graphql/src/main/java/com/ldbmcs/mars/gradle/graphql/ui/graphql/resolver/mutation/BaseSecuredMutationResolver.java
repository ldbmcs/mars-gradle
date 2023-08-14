package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.mutation;

import com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.SecuredResolver;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.SystemGraphQLResolver;
import org.springframework.validation.annotation.Validated;

@Validated
public abstract class BaseSecuredMutationResolver implements SystemGraphQLResolver<Void>, SecuredResolver {

}
