package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class Quiz {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .subscribe(new Subscriber<Integer>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
//                        subscription.request(10); // ############## add this
                    }

                    @Override
                    public void onNext(Integer next) {
                        System.out.println(next);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("done");
                    }

                });


        Util.sleepSeconds(1);
    }

}
