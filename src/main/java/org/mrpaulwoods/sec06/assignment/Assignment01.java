package org.mrpaulwoods.sec06.assignment;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

public class Assignment01 {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Assignment01.class);

    private final OrderPublisher orderPublisher = new OrderPublisher();
    private final QuantitySubscriber quantitySubscriber = new QuantitySubscriber();
    private final RevenueSubscriber revenueSubscriber = new RevenueSubscriber();

    public static void main(String[] args) {
        new Assignment01().run();
    }

    public void run() {

        Flux<Order> flux = orderPublisher
                .getOrderSteam()
                .publish()
                .autoConnect(2);

        flux.subscribe(quantitySubscriber);
        flux.subscribe(revenueSubscriber);

        Util.sleepSeconds(5);

        log.info("quantities remaining by category: {}", quantitySubscriber.getCategoryValue());
        log.info("revenue increase by category: {}", revenueSubscriber.getCategoryValue());
    }

}
