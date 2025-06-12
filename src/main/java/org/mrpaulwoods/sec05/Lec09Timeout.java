package org.mrpaulwoods.sec05;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec09Timeout {

    public static void main(String[] args) {
        getProductName()
//                .timeout(Duration.ofSeconds(3), fallback())
                .timeout(Duration.ofSeconds(1), fallback())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "service-" + Util.faker().commerce().productName())
                .delayElement(Duration.ofSeconds(2));
    }

    private static Mono<String> fallback() {
        return Mono.fromSupplier(() -> "fallback-" + Util.faker().commerce().productName())
                .delayElement(Duration.ofSeconds(1));
    }
}
