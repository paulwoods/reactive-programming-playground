package org.mrpaulwoods.sec09.assignment;

import org.mrpaulwoods.common.Util;

public class Lec08ZipAssignment {

    // /demo05
    public static void main(String[] args) {

        ExternalServiceClient client = new ExternalServiceClient();

        for (int n = 0; n < 10; n++) {
            client.getItem(n).subscribe(Util.subscriber());
        }

        Util.sleepSeconds(2);

    }

}
