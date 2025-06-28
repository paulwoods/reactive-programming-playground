package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    To collect the items received via Flux. Assuming we will have finite items
 */
public class Lec14CollectList {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .concatWith(Mono.error(new RuntimeException("boom")))
                .collectList()
                .subscribe(System.out::println);

        Util.sleepSeconds(2);
    }

}
