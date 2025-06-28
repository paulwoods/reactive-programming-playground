package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec09.assignment.ExternalServiceClient;

public class Lec08ZipAssignment {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        for (int i = 1; i < 10; ++i) {
            client.getProduct(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(2);
    }

}
