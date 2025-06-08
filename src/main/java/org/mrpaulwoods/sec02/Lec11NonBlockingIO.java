package org.mrpaulwoods.sec02;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec11NonBlockingIO {

    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        log.info("starting");

        // concurrent
        for (int i = 1; i <= 100; ++i) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        // block
//        for(int i = 1; i <= 100; ++i) {
//            String name = client.getProductName(i)
//                    .block();
//            System.out.println(name);
//        }

        Util.sleepSeconds(2);

    }

}
