package org.mrpaulwoods.sec06.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseSubscriber implements Subscriber<Order> {

    protected Map<String, Integer> categoryValue = new ConcurrentHashMap<>();

    public Map<String, Integer> getCategoryValue() {
        return categoryValue;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }

}
