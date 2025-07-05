package org.mrpaulwoods.tests;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec04RangeTest {

    private Flux<Integer> getItems() {
        return Flux.range(1, 50)
                .log();
    }

    private Flux<Integer> getRandomItems() {
        return Flux.range(1, 50)
                .map(i -> Util.faker().random().nextInt(1, 100))
                .log();
    }

    @Test
    public void fluxTest1() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectNextCount(45)
                .expectNext(49, 50)
                .expectComplete()
                .verify();
    }

    @Test
    public void fluxTest2() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3)
                .expectNextCount(22)
                .expectNext(26, 27, 28)
                .expectNextCount(22)
                .expectComplete()
                .verify();
    }

    @Test
    public void fluxTest3() {
        StepVerifier.create(getRandomItems())
                .expectNextMatches(i -> i > 0 && i < 101)
                .expectNextCount(49)
                .expectComplete()
                .verify();
    }

    @Test
    public void fluxTest4() {
        StepVerifier.create(getRandomItems())
                .thenConsumeWhile(i -> i > 0 && i < 101)
                .expectComplete()
                .verify();
    }

}
