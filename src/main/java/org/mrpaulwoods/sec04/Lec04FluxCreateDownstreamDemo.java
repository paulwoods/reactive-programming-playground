package org.mrpaulwoods.sec04;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateDownstreamDemo {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemo.class);

    public static void main(String[] args) {

        // flux create does not check the downstream demand by default! it is by design!

        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                var name = Util.faker().country().name();
                log.info("generated name: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();

        // all items are generated, even if they are not requested
        // this is by design
    }

}
