package org.mrpaulwoods.sec11;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec11.client.ExternalServiceClient;
import org.mrpaulwoods.sec11.client.ServerError;
import org.slf4j.Logger;
import reactor.util.retry.Retry;

import java.time.Duration;

public class Lec03ExternalServiceDemo {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec03ExternalServiceDemo.class);

    public static void main(String[] args) {
//        repeat();
        retry();
        Util.sleepSeconds(60);
    }

    private static void repeat() {
        var client = new ExternalServiceClient();
        client.getCountry()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("Canada"))
                .subscribe(Util.subscriber());
    }

    private static void retry() {
        var client = new ExternalServiceClient();
        client.getProductName(2)
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber());
    }

    private static Retry retryOnServerError() {
        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(ex -> ServerError.class.equals(ex.getClass()))
                .doBeforeRetry(ex -> log.info("Retrying {} {}", ex.totalRetriesInARow(), ex.failure().getMessage()));
    }

}
