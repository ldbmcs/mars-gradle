package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver;

import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SystemGraphQLResolver<T> extends GraphQLResolver<T> {
}
