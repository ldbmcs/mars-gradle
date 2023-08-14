package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver;

import com.ldbmcs.mars.gradle.graphql.infra.context.CurrentContext;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.execution.GraphqlDataFetcherExceptionHandler;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.execution.ExecutionStrategy;
import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.kickstart.servlet.core.GraphQLServletListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static graphql.kickstart.autoconfigure.web.servlet.GraphQLWebAutoConfiguration.*;


@Configuration
public class GraphQLWebConfiguration {
    @Bean
    public Map<String, ExecutionStrategy> executionStrategies() {
        GraphqlDataFetcherExceptionHandler handler = new GraphqlDataFetcherExceptionHandler();
        return Map.of(
                QUERY_EXECUTION_STRATEGY, new AsyncExecutionStrategy(handler),
                MUTATION_EXECUTION_STRATEGY, new AsyncSerialExecutionStrategy(handler),
                SUBSCRIPTION_EXECUTION_STRATEGY, new AsyncExecutionStrategy(handler)
        );
    }

    @Bean
    public List<GraphQLServletListener> listeners() {
        return List.of(new GraphQLServletListener() {
            @Override
            public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
                return new RequestCallback() {
                    @Override
                    public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                        CurrentContext.clear();
                    }
                };
            }
        });
    }

    @Bean(name = "servlet")
    public GraphQLHttpServlet overrideGraphQLHttpServlet(GraphQLConfiguration graphQLConfiguration) {
        return GraphQLHttpServlet.with(graphQLConfiguration);
    }

}
