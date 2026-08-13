package com.lavalliere.daniel.spring.httpinterface.service;

import org.jspecify.annotations.Nullable;
import org.springframework.core.retry.*;
import org.springframework.stereotype.Component;

@Component
public class TodoRetryListener implements RetryListener {

    // Example of overridable methods for the RetryListener

    @Override
    public void onRetryableExecution(RetryPolicy retryPolicy, Retryable<?> retryable, RetryState retryState) {
        RetryListener.super.onRetryableExecution(retryPolicy, retryable, retryState);
    }

    @Override
    public void beforeRetry(RetryPolicy retryPolicy, Retryable<?> retryable, RetryState retryState) {
        RetryListener.super.beforeRetry(retryPolicy, retryable, retryState);
    }

    @Override
    public void beforeRetry(RetryPolicy retryPolicy, Retryable<?> retryable) {
        RetryListener.super.beforeRetry(retryPolicy, retryable);
    }

    @Override
    public void onRetrySuccess(RetryPolicy retryPolicy, Retryable<?> retryable, @Nullable Object result) {
        RetryListener.super.onRetrySuccess(retryPolicy, retryable, result);
    }

    @Override
    public void onRetryFailure(RetryPolicy retryPolicy, Retryable<?> retryable, Throwable throwable) {
        RetryListener.super.onRetryFailure(retryPolicy, retryable, throwable);
    }

    @Override
    public void onRetryPolicyExhaustion(RetryPolicy retryPolicy, Retryable<?> retryable, RetryException exception) {
        RetryListener.super.onRetryPolicyExhaustion(retryPolicy, retryable, exception);
    }

    @Override
    public void onRetryPolicyInterruption(RetryPolicy retryPolicy, Retryable<?> retryable, RetryException exception) {
        RetryListener.super.onRetryPolicyInterruption(retryPolicy, retryable, exception);
    }

    @Override
    public void onRetryPolicyTimeout(RetryPolicy retryPolicy, Retryable<?> retryable, RetryException exception) {
        RetryListener.super.onRetryPolicyTimeout(retryPolicy, retryable, exception);
    }
}
