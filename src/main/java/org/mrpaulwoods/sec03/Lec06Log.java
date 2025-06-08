package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {

    public static void main(String[] args) {

        Flux.range(1, 5)
                .log("range")
                .map(i -> Util.faker().name().firstName())
                .log("name")
                .subscribe(Util.subscriber());
    }

}
