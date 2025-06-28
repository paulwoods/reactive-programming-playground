package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec09.applications.PaymentService;
import org.mrpaulwoods.sec09.applications.UserService;

/*
    Sequential non-blocking IO Calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher

       - used when the inner function returns a mono
 */
public class Lec09MonoFlatMap {

    public static void main(String[] args) {

        /*
        We have username.
        Get user account balance.
        */

        // doesn't work
//        Mono<Mono<Integer>> mono = UserService.getUserId("sam")
//                .map(UserId -> PaymentService.getUserBalance(userId));

        // cant use .map, because getUserBalance returns a mono we need to subscribe to.
        // instead, use flat map.

        UserService.getUserId("sam")
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

}
