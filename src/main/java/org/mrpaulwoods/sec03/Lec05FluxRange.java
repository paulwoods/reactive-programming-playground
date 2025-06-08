package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {

    public static void main(String[] args) {

        // prints 3 through 12
        Flux.range(3, 10)
                .subscribe(Util.subscriber());

        // generate 10 random first names

        Flux.range(1, 10)
                .map(i -> Util.faker().name().firstName())
                .subscribe(Util.subscriber());
    }

}
