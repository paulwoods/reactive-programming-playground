package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.slf4j.LoggerFactory.getLogger;

public class Lec05Merge {

    private static final Logger log = getLogger(Lec05Merge.class);

    public static void main(String[] args) {
        demo2();
        Util.sleepSeconds(3);
    }

    private static void demo1() {

        // you don't control who you get items from
        // the order that they publish controls the order
        Flux.merge(producer1(), producer2(), producer3())
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        producer2()
                .mergeWith(producer1())
                .mergeWith(producer3())
                .take(2)
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .transform(Util.fluxLogger("producer1"))
                .doOnSubscribe(s -> log.info("subscribing to producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .transform(Util.fluxLogger("producer2"))
                .doOnSubscribe(s -> log.info("subscribing to producer2"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer3() {
        return Flux.just(11, 12, 13)
                .transform(Util.fluxLogger("producer3"))
                .doOnSubscribe(s -> log.info("subscribing to producer3"))
                .delayElements(Duration.ofMillis(10));
    }

}
