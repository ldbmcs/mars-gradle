package com.ldbmcs.mars.gradle.graphql.common.exceptions;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ServiceWithDetailException extends ServiceException {
    private Map<String, Object> details;

    public Map<String, Object> getDetails() {
        return details;
    }

    public ServiceWithDetailException(@NotNull String code, @Nullable Object... arguments) {
        super(code, arguments);
    }

    public ServiceWithDetailException(@NotNull String code, Map<String, Object> details, @Nullable Object... arguments) {
        super(code, arguments);
        this.details = details;
    }

    public ServiceWithDetailException(@NotNull String code, Map<String, Object> details, @Nullable Throwable cause,
                                      @Nullable Object... arguments) {
        super(code, cause, arguments);
        this.details = details;
    }
}
