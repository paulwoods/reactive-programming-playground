package org.mrpaulwoods.sec04;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    public static void main(String[] args) {

        // we only generate one value.
        // our closure gets calls
        Flux.<String>generate(synchronousSink -> {
                    synchronousSink.next(Util.faker().country().name());
                })
                .takeWhile(name -> !name.equalsIgnoreCase("Canada"))
                .subscribe(Util.subscriber());
    }

}
