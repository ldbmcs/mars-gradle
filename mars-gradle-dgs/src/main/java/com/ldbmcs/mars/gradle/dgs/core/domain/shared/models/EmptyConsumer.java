package com.ldbmcs.mars.gradle.dgs.core.domain.shared.models;

import java.util.Objects;

@FunctionalInterface
public interface EmptyConsumer {

    void accept();

    default EmptyConsumer andThen(EmptyConsumer after) {
        Objects.requireNonNull(after);
        return () -> {
            accept();
            after.accept();
        };
    }
}
