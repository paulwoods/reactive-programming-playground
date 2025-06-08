package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxJust {

    public static void main(String[] args) {
        Flux.just(1)
                .subscribe(Util.subscriber());

        Flux.just(11, 12, 13, 14, 15, "Hello")
                .subscribe(Util.subscriber());
    }
}
