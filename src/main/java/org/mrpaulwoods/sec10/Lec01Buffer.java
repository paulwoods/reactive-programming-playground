package org.mrpaulwoods.sec10;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec01Buffer {

    public static void main(String[] args) {
        demo4();

        Util.sleepSeconds(10);
    }

    private static void demo1() {
        eventStream()
                .buffer() // collect Integer.MAX number of items
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        eventStream()
                .buffer(3) // collect every 3 items
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        eventStream()
                .buffer(Duration.ofMillis(500)) // collect all items for 500ms
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        eventStream()
                .bufferTimeout(3, Duration.ofSeconds(1)) // collect every 3 items or max 1 second
                .subscribe(Util.subscriber());
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .take(10)
//                .concatWith(Flux.never()) // doesn't send complete signal - used for testing
                .map(i -> "event-" + (i + 1));
    }

}
