package org.mrpaulwoods.sec06;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    - publish().autoConnect(0) will provide new values to the subscribers
    - replay allows us to cache
 */
public class Lec04HotPublisherCache {


    private static final Logger log = LoggerFactory.getLogger(Lec04HotPublisherCache.class);

    public static void main(String[] args) {

        var stockFlux = stockStream().replay(3).autoConnect(0);

        Util.sleepSeconds(4);

        log.info("sam joining");

        stockFlux
                .subscribe(Util.subscriber("sam"));

        Util.sleepSeconds(4);

        log.info("mike joining");

        stockFlux
                .subscribe(Util.subscriber("mike"));

        Util.sleepSeconds(15);
    }

    // movie theater
    private static Flux<Integer> stockStream() {
        return Flux.<Integer>generate(sink -> sink.next(Util.faker().random().nextInt(10, 100)))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(price -> log.info("emitting price: {}", price))
                ;
    }

}
