package org.mrpaulwoods.sec13;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec13.client.ExternalServiceClient;
import reactor.util.context.Context;

public class Lec04ContextRateLimiterDemo {

    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();
//        demo4();
//        demo5();
        demo6();

        Util.sleepSeconds(5);
    }

    // non-rate limited example
    public static void demo1() {

        var client = new ExternalServiceClient();

        for (int i = 0; i < 10; i++) {
            client.getBook()
                    .subscribe(Util.subscriber());
        }
    }

    // rate limited example - no user
    public static void demo2() {

        var client = new ExternalServiceClient();

        for (int i = 0; i < 10; i++) {
            client.getBookLimited()
                    .subscribe(Util.subscriber());
        }

    }

    // rate limited example
    public static void demo3() {

        var client = new ExternalServiceClient();

        for (int i = 0; i < 10; i++) {
            client.getBookLimited()
                    .contextWrite(Context.of("user", "mike"))
                    .subscribe(Util.subscriber());
        }

    }

    // attempted hack by setting the category directly - doesn't work
    public static void demo4() {

        var client = new ExternalServiceClient();

        for (int i = 0; i < 10; i++) {
            client.getBookLimited()
                    .contextWrite(Context.of("category", "prime"))
                    .subscribe(Util.subscriber());
        }

    }

    // users with no category
    public static void demo5() {

        var client = new ExternalServiceClient();

        for (int i = 0; i < 10; i++) {
            client.getBookLimited()
                    .contextWrite(Context.of("user", "jake"))
                    .subscribe(Util.subscriber());
        }

    }

    // test the 5 second refresh
    // by making a call once-per second, we should see
    // mike: 3 success and 2 fails every 5 seconds
    // sam: 2 success and 3 fails every 5 seconds
    public static void demo6() {

        var client = new ExternalServiceClient();

        for (int i = 0; i < 20; i++) {
            client.getBookLimited()
                    .contextWrite(Context.of("user", "mike"))
                    .subscribe(Util.subscriber());

            Util.sleepSeconds(1);
        }

    }

}
