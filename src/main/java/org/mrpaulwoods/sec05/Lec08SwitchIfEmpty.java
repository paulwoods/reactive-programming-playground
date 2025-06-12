package org.mrpaulwoods.sec05;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec08SwitchIfEmpty {

    private static final Logger log = LoggerFactory.getLogger(Lec08SwitchIfEmpty.class);

    public static void main(String[] args) {

        Flux.range(1, 10)
                .filter(i -> i > 11)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 3);
    }
}
