package com.ldbmcs.mars.gradle.graphql.ui.graphql.execution;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ResultPath;
import graphql.language.SourceLocation;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionException;

@Slf4j
public class GraphqlDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters parameters) {
        Throwable exception = unrollException(parameters.getException());
        CodedExceptionWhileDataFetching error = buildError(parameters, exception);
        return DataFetcherExceptionHandlerResult.newResult().error(error).build();
    }

    private CodedExceptionWhileDataFetching buildError(DataFetcherExceptionHandlerParameters parameters, Throwable exception) {
        SourceLocation sourceLocation = parameters.getSourceLocation();
        ResultPath path = parameters.getPath();
        return new CodedExceptionWhileDataFetching(path, exception, sourceLocation);
    }

    private Throwable unrollException(Throwable exception) {
        if (exception instanceof CompletionException) {
            return exception.getCause();
        }
        return exception;
    }
}
