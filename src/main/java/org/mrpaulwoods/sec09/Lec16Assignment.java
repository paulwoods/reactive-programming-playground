package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec09.applications.*;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec16Assignment {

    public static void main(String[] args) {

        // get all users, build user information

        UserService.getAllUsers()
                .flatMap(Lec16Assignment::getUserInformation)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Mono<UserInformation> getUserInformation(User user) {
        return Mono.zip(
                        PaymentService.getUserBalance(user.id()),
                        OrderService.getUserOrders(user.id()).collectList()
                )
                .map(t -> new UserInformation(
                        user.id(),
                        user.username(),
                        t.getT1(),
                        t.getT2()
                ));
    }

    record UserInformation(
            Integer userId,
            String username,
            Integer balance,
            List<Order> orders) {
    }

}
