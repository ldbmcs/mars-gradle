package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver;

import com.ldbmcs.mars.gradle.graphql.core.domain.auth.service.AuthTokenDomainService;
import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.graphql.infra.context.CurrentContext;
import com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.dataloader.DataLoaderMapper;
import graphql.com.google.common.base.Strings;
import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLKickstartContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {
    @Autowired
    private DataLoaderMapper dataLoaderMapper;
    @Autowired
    private AuthTokenDomainService authTokenDomainService;

    @Override
    public GraphQLKickstartContext build() {
        return new DefaultGraphQLContext(buildDataLoaderRegistry());
    }

    @Override
    public GraphQLKickstartContext build(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        CustomGraphQLContext customGraphQLContext = new CustomGraphQLContext(buildDataLoaderRegistry());
        String authorization = request.getHeader("Authorization");
        if (Strings.isNullOrEmpty(authorization)) {
            return customGraphQLContext;
        }

        User currentUser = authTokenDomainService.parse(authorization);
        if (currentUser != null) {
            CurrentContext.setAuthToken(authorization);
            CurrentContext.setUser(currentUser);
            customGraphQLContext.addCurrentAccountUser(currentUser);
        }
        return customGraphQLContext;
    }

    @Override
    public GraphQLKickstartContext build(Session session, HandshakeRequest handshakeRequest) {
        return DefaultGraphQLWebSocketContext.createWebSocketContext()
            .with(session)
            .with(handshakeRequest)
            .with(buildDataLoaderRegistry())
            .build();
    }

    private DataLoaderRegistry buildDataLoaderRegistry() {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        dataLoaderMapper.buildMap().forEach(registry::register);
        return registry;
    }
}
