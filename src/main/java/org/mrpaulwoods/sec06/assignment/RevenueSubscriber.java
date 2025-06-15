package org.mrpaulwoods.sec06.assignment;

import org.slf4j.Logger;

public class RevenueSubscriber extends BaseSubscriber {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RevenueSubscriber.class);

    @Override
    public void onNext(Order order) {
        categoryValue.compute(
                order.category(),
                (k, v) -> (v == null ? 0 : v) + order.price()
        );
        log.info("increased revenue of {} by {} to {}", order.category(), order.price(), categoryValue.get(order.category()));
    }

}
