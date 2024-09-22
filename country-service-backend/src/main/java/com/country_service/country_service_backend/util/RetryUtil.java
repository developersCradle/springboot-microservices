package com.country_service.country_service_backend.util;

import java.time.Duration;

import com.country_service.country_service_backend.exceptionhandler.CountriesNowServerException;

import reactor.core.Exceptions;
import reactor.util.retry.Retry;

public class RetryUtil {

	/*
	 * Retry strategy for call external API.
	 */

	public static Retry retrySpec() {
    	
    	// When Retry Exhausted is thrown, what we want to do, we are throwing it to client!
        return  Retry.fixedDelay(3, Duration.ofSeconds(1))
            .filter(ex -> ex instanceof CountriesNowServerException) // We don't want to re-try to happen in case of 4xx resource not found, since there is no use to retry in such scenario.
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure()));
    }
}
