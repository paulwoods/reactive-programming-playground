package org.mrpaulwoods.sec02;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec09PublisherCreateVsExecution {

    public static final Logger log = LoggerFactory.getLogger(Lec09PublisherCreateVsExecution.class);

    public static void main(String[] args) {

        getName()
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getName() {
        return Mono.fromSupplier(() -> {
            log.info("generating name");
            return Util.faker().name().fullName();
        });
    }

}
