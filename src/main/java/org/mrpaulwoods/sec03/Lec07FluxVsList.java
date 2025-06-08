package org.mrpaulwoods.sec03;

import org.mrpaulwoods.sec01.subscriber.SubscriberImpl;
import org.mrpaulwoods.sec03.helper.NameGenerator;

public class Lec07FluxVsList {

    public static void main(String[] args) {

        // list approach
//        var list = NameGenerator.getNamesList(10);
//        System.out.println(list);

        // flux approach
//        NameGenerator.getNamesFlux(10)
//                .subscribe(Util.subscriber());

        // custom subscription
        // you can cancel the subscription once you have all the data you need

        var subscriber = new SubscriberImpl();
        NameGenerator.getNamesFlux(10)
                .subscribe(subscriber);

        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
    }

}
