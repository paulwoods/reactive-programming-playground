package org.mrpaulwoods.sec05;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06ErrorHandling {

    public static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {

        demo5();
    }

    private static Mono<Integer> fallback1() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

    private static Mono<Integer> fallback2() {
//        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000))
        return Mono.error(new IllegalArgumentException("oops"));
    }

    private static void demo1() {

        // handle errors by exception class, with a default

        Flux.range(1, 10)
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    public static void demo2(String[] args) {

        //on error, call a fallback method to get the value

        Mono.just(5)
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

    public static void demo3() {

        // shows that if the fallbacks give errors, they can be handled.

        Mono.<Integer>error(new RuntimeException("oops"))
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

    public static void demo4() {

        // on any exception, dont emit it, but complete the pubsub.

        Mono.<Integer>error(new RuntimeException("oops"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    public static void demo5() {

        // skip the error and continue the flux

        Flux.range(1, 10)
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorContinue((ex, obj) -> log.error("==> {}", obj, ex))
                .subscribe(Util.subscriber());

    }

}
