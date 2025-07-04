package org.mrpaulwoods.tests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01MonoTest {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec01MonoTest.class);

    private Mono<String> getProduct(int id) {
        return Mono.fromSupplier(() -> "product-" + id)
                .doFirst(() -> log.info("invoked"));
    }

    @Test
    public void productTest() {
        StepVerifier.create(getProduct(2))
                .expectNext("product-1")
                .expectComplete()
                .verify();
    }

}
