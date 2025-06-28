package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec09.applications.OrderService;
import org.mrpaulwoods.sec09.applications.UserService;

/*
    Sequential non-blocking IO Calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
    Mono is supposed to be 1 item - what if the flatMap returns multiple items?
 */
public class Lec10MonoFlatMapMany {

    public static void main(String[] args) {

        /*
        We have username.
        Get user's orders.
        */

        UserService.getUserId("sam")
                .flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);

    }

}
