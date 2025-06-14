package org.mrpaulwoods.sec06;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec01ColdPublisher {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec01ColdPublisher.class);

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        var flux = Flux.create(fluxSink -> {
            log.info("invoked");
            for (int n = 0; n < 3; n++) {
                fluxSink.next(atomicInteger.incrementAndGet());
            }
            fluxSink.complete();
        });

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
    }

    // each subscriber gets the full content of the sink
    // if the sink has state outside the closure, then that is shared between allof the sinks

}
