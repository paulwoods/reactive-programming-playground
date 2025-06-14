package org.mrpaulwoods.sec05.assignment;

import org.mrpaulwoods.common.Util;

public class Assignment01 {

    public static void main(String[] args) {

        ExternalServiceClient client = new ExternalServiceClient();

        for (int n = 1; n < 5; ++n) {
            client.getProduct(n)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(5);
    }

}
