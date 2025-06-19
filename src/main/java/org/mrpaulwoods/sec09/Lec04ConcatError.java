package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.slf4j.LoggerFactory.getLogger;

public class Lec04ConcatError {

    private static final Logger log = getLogger(Lec04ConcatError.class);

    public static void main(String[] args) {

        demo2();

        Util.sleepSeconds(3);
    }

    private static void demo1() {
        producer1()
                .concatWith(producer3())
                .concatWith(producer2())
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        Flux.concatDelayError(producer1(), producer3(), producer2())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> producer1() {
        return Flux.just(1, 2, 3)
                .doOnSubscribe(s -> log.info("subscribing to producer1"))
                .delayElements(Duration.ofMillis(10));
    }

    private static Flux<Integer> producer2() {
        return Flux.just(51, 52, 53)
                .doOnSubscribe(s -> log.info("subscribing to producer2"))
                .delayElements(Duration.ofMillis(10));
    }


    private static Flux<Integer> producer3() {
        return Flux
                .error(new RuntimeException("Something went wrong"))
                .doOnSubscribe(s -> log.info("subscribing to producer3"))
                .cast(Integer.class);
    }

}
