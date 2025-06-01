package org.mrpaulwoods.sec02;

import org.mrpaulwoods.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec02MonoJust {

    private final static Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {

        var mono = Mono.just("vins");
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        subscriber.getSubscription().request(10);
        subscriber.getSubscription().request(10);
        subscriber.getSubscription().cancel();
    }

}
