package org.mrpaulwoods.sec01.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);
    private static final int MAX_ITEMS = 10;
    private final Faker faker;
    private final Subscriber<? super String> subscriber;
    private boolean isCanceled = false;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if (isCanceled) {
            return;
        }

        log.info("subscriber has requested {} items", requested);

        if (requested > MAX_ITEMS) {
            this.subscriber.onError(new RuntimeException("Requested too many items"));
            this.isCanceled = true;
            return;
        }

        for (int i = 0; i < requested && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(this.faker.internet().emailAddress());
        }

        if (count == MAX_ITEMS) {
            log.info("no more data to produce");
            this.subscriber.onComplete();
            this.isCanceled = true;
        }
    }

    @Override
    public void cancel() {
        log.info("subscriber has canceled");
        this.isCanceled = true;
    }

}
