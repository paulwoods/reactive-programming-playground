package org.mrpaulwoods.sec04;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = Util.faker().country().name();
                        fluxSink.next(country);


                    } while (!country.equalsIgnoreCase("Canada"));
                })
                .subscribe(Util.subscriber());
    }

}
