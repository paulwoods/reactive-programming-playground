package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec10FluxEmptyError {

    public static void main(String[] args) {

        Flux.empty()
                .subscribe(Util.subscriber());

        Flux.error(new RuntimeException("Something went wrong"))
                .subscribe(Util.subscriber());

    }

}
