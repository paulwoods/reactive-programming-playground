package org.mrpaulwoods.sec03.assignment;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec03.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sec03Assignment {

    private static final Logger log = LoggerFactory.getLogger(Sec03Assignment.class);

    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        var subscriber = new StockPriceSubscriber();

        client
                .getPriceChanges()
                .subscribe(subscriber);

        Util.sleepSeconds(20);
    }

}
