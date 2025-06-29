package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class lec15Then {

    public static final Logger log = LoggerFactory.getLogger(lec15Then.class);

    public static void main(String[] args) {
        log.info("start");

        // save the records
        // if all saved successfully, send emails
        var records = List.of("a", "b", "c");

        saveRecords(records)
                .then(sendNotification(records))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                .map(r -> "saved " + r)
                .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> log.info("all these {} records saved successfully", records));
    }

}
