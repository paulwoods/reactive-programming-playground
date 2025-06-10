package org.mrpaulwoods.sec04;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {

    private static final Logger log = LoggerFactory.getLogger(Lec07FluxGenerateUntil.class);

    public static void main(String[] args) {

//        Flux.generate(synchronousSink -> {
//                    var country = Util.faker().country().name();
//                    synchronousSink.next(country);
//                    if (country.equalsIgnoreCase("Canada")) {
//                        synchronousSink.complete();
//                    }
//                })
//                .subscribe(Util.subscriber());

        Flux.<String>generate(synchronousSink -> {
                    var country = Util.faker().country().name();
                    synchronousSink.next(country);
                })
                .takeUntil(name -> name.equalsIgnoreCase("Canada"))
                .subscribe(Util.subscriber());
    }

}
