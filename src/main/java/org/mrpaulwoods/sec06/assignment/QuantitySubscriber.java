package org.mrpaulwoods.sec06.assignment;

import org.slf4j.Logger;

public class QuantitySubscriber extends BaseSubscriber {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QuantitySubscriber.class);

    @Override
    public void onNext(Order order) {
        categoryValue.compute(
                order.category(),
                (k, v) -> (v == null ? 500 : v) - order.quantity()
        );

        log.info("decreased quantity of {} by {} to {}", order.category(), order.quantity(), categoryValue.get(order.category()));
    }

}
