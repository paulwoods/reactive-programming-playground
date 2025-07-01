package org.mrpaulwoods.sec11;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
    retry operator simply resubscribes when it sees error signal.
 */
public class Lec02Retry {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec02Retry.class);

    public static void main(String[] args) {
        demo6();
    }

    public static void demo1() {
        getCountryName()
                .subscribe(Util.subscriber());
    }

    public static void demo2() {
        getCountryName()
                .retry(3)
                .subscribe(Util.subscriber());
    }

    public static void demo3() {
        getCountryName()
//                .retryWhen(Retry.indefinitely())
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(1)))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    public static void demo4() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
                                .doBeforeRetry(retrySignal -> log.info("retrying {}", retrySignal.totalRetriesInARow()))
                )
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    // retry on certain exceptions
    public static void demo5() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
//                                .filter(ex -> IllegalArgumentException.class.equals(ex.getClass()))
                                .filter(ex -> RuntimeException.class.equals(ex.getClass()))

                )
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    // Don't throw RetryExhausted exception. Throw the original exception
    public static void demo6() {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(1, Duration.ofSeconds(1))
                                .filter(ex -> RuntimeException.class.equals(ex.getClass()))
                                .onRetryExhaustedThrow((spec, signal) -> signal.failure())

                )
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static Mono<String> getCountryName() {
        var atomicInteger = new AtomicInteger(0);
        return Mono.fromSupplier(() -> {
                    if (atomicInteger.incrementAndGet() < 3) {
                        throw new RuntimeException("Boom!");
                    }
                    return Util.faker().country().name();
                })
                .doOnError(e -> log.info("error: {}", e.getMessage()))
                .doOnSubscribe(s -> log.info("subscribing"));
    }

}
