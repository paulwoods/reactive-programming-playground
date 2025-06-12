package org.mrpaulwoods.sec05;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec07DefaultIfEmpty {

    // similar to error handling
    // how to handle empty

    public static void main(String[] args) {

        Mono.empty()
                .defaultIfEmpty("fallback value")
                .subscribe(Util.subscriber());

        Flux.range(1, 10)
                .filter(i -> i > 11)
                .defaultIfEmpty(-1)
                .subscribe(Util.subscriber());

    }

}
