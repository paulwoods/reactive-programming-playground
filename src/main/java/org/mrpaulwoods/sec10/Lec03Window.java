package org.mrpaulwoods.sec10;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec03Window {

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        eventStream()
                .window(5)
                .flatMap(Lec03Window::processEvents)
                .subscribe();

        Util.sleepSeconds(10);
    }

    private static void demo2() {
        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(Lec03Window::processEvents)
                .subscribe();

        Util.sleepSeconds(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        return flux.doOnNext(_ -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }

}
