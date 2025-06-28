package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec09.applications.OrderService;
import org.mrpaulwoods.sec09.applications.User;
import org.mrpaulwoods.sec09.applications.UserService;

public class Lec11FluxFlatMap {

    public static void main(String[] args) {

        /*
            get all the orders from order service
         */

        // get all users
        // get each user's orders

        UserService.getAllUsers()
                .map(User::id)
                .flatMap(OrderService::getUserOrders, 1)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

}
