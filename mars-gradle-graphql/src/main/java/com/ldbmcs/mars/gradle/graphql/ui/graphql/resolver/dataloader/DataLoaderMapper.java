package com.ldbmcs.mars.gradle.graphql.ui.graphql.resolver.dataloader;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataLoaderMapper {

    @Autowired
    private UserBatchLoader userBatchLoader;

    public Map<String, DataLoader<?, ?>> buildMap() {
        DataLoaderOptions batchOptions = DataLoaderOptions.newOptions();
        return Map.of(
                DataLoaderIdentity.USER, DataLoaderFactory.newMappedDataLoader(userBatchLoader.getById(), batchOptions)
        );
    }
}
