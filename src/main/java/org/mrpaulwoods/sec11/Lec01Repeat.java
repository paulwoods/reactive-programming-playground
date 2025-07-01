package org.mrpaulwoods.sec11;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/*
    the repeat operator simply resubscribes when it sees complete signal.
    it does not like the error signal.

    repeat re-subscribes after it receives the complete signal.

 */
public class Lec01Repeat {

    public static void main(String[] args) {
        demo5();
    }

    public static void demo1() {

        var mono = Mono.fromSupplier(() -> Util.faker().country().name());
        var subscriber = Util.subscriber();

        mono.repeat(3)
                .subscribe(subscriber);
    }

    public static void demoX() {

        // don't do this - use .repeat(x) instead.
        // this works because everything is in memory.
        // and the supplier is a non-blocking io operation.

        var mono = Mono.fromSupplier(() -> Util.faker().country().name());
        var subscriber = Util.subscriber();

        for (int i = 0; i < 3; i++) {
            mono.subscribe(subscriber);
        }

    }

    public static void demo2() {

        getCountryName()
                .repeat()
                .takeUntil(s -> s.equalsIgnoreCase("Canada"))
                .subscribe(Util.subscriber());
    }

    public static void demo3() {
        var atomicInteger = new AtomicInteger(0);

        getCountryName()
                .repeat(() -> atomicInteger.incrementAndGet() < 3)
                .subscribe(Util.subscriber());
    }

    public static void demo4() {
        var atomicInteger = new AtomicInteger(0);

        getCountryName()
                .repeatWhen(flux -> flux
                                .delayElements(Duration.ofSeconds(2))
//                        .take(2) // limit to two repeats
                )
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    public static void demo5() {
        Flux.just(1, 2, 3)
                .repeat(3)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

    private static Mono<String> getCountryName() {
        return Mono.fromSupplier(() -> Util.faker().country().name()); // non-blocking IO
    }

}
