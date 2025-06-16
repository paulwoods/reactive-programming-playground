package org.mrpaulwoods.sec07;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec07.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec06EventLoopIssueFix {

    private static final Logger log = LoggerFactory.getLogger(Lec06EventLoopIssueFix.class);

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        for (int i = 1; i <= 5; ++i) {
            client.getProductName(i)
                    .map(m -> process(m))
                    .subscribe(Util.subscriber());
        }
        // ExternalServiceClient adds a publishOn method

        Util.sleepSeconds(10);
    }

    private static String process(String input) {
        Util.sleepSeconds(1);
        return input + "-processed";
    }

}
