package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.dataloader;

import com.ldbmcs.mars.gradle.graphql.core.domain.user.models.User;
import com.ldbmcs.mars.gradle.graphql.core.domain.user.service.UserDomainService;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

@Component
public class UserBatchLoader {

    @Autowired
    private UserDomainService userDomainService;

    public MappedBatchLoader<String, User> getById() {
        return ids -> CompletableFuture.completedFuture(userDomainService.findAllById(new HashSet<>(ids)));
    }
}
