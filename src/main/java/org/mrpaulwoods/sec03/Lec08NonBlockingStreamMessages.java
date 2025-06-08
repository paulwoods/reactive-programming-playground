package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec03.client.ExternalServiceClient;

public class Lec08NonBlockingStreamMessages {

    public static void main(String[] args) {

//        var client = new ExternalServiceClient();
//        client.getNames().subscribe(Util.subscriber());
//        Util.sleepSeconds(6);

        var client = new ExternalServiceClient();
        client.getNames().subscribe(Util.subscriber("sub1"));
        client.getNames().subscribe(Util.subscriber("sub2"));
        Util.sleepSeconds(6);
    }

}
