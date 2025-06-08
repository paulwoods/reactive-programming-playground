package org.mrpaulwoods.sec04;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec05TakeOperator {

    public static void main(String[] args) {

        // take()
//        takeWhile();
        takeUntil();
    }

    private static void take() {
        Flux.range(1, 10)
                .log("before")
                .take(3)
                .log("after")
                .subscribe(Util.subscriber());

    }

    private static void takeWhile() {

        // stop when the condition is not met
        // the matching condition is *NOT* sent to the subscriber
        Flux.range(1, 10)
                .log("before")
                .takeWhile(i -> i < 5)
                .log("after")
                .subscribe(Util.subscriber());

    }

    private static void takeUntil() {

        // stop when the condition is met
        // the matching condition is sent to the subscriber

        Flux.range(1, 10)
                .log("before")
                .takeUntil(i -> i == 4)
                .log("after")
                .subscribe(Util.subscriber());

    }

}
