package org.mrpaulwoods.sec03.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceSubscriber implements Subscriber<Integer> {

    private static final Logger log = LoggerFactory.getLogger(StockPriceSubscriber.class);
    private int quantity = 0;
    private int balance = 1000;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Integer price) {
        if (price < 90) {
            if (balance >= price) {
                quantity++;
                balance -= price;
                log.info("+++ buying stock at {}. total quantity: {}. balance {}", price, quantity, balance);
            }
        } else if (price > 110 && quantity > 0) {
            log.info("--- selling {} stock at {}", quantity, price);
            balance += (quantity * price);
            quantity = 0;
            subscription.cancel();
            System.out.println("profit made: " + (balance - 1000));
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error", throwable);
    }

    @Override
    public void onComplete() {
        log.info("completed!");
    }

    public Subscription getSubscription() {
        return subscription;
    }

}
