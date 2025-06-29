package org.mrpaulwoods.sec10.assignment.groupby;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec06GroupByAssignment {

    public static void main(String[] args) {

        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(gf -> gf.transform(OrderProcessingService.getProcessor(gf.key())))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    public static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(o -> PurchaseOrder.create());
    }


}
