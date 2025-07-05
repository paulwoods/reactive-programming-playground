package org.mrpaulwoods.tests;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.util.function.UnaryOperator;

public class Lec09PublisherTest {

    private UnaryOperator<Flux<String>> processor() {
        return flux -> flux
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase)
                .map(s -> s + ":" + s.length());
    }

    @Test
    public void publisherTest1() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        flux.subscribe(Util.subscriber());

//        publisher.next("a", "b");
//        publisher.complete();
        publisher.emit("a", "b"); // send 2 next and the complete signals
    }

    @Test
    public void publisherTest2() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        StepVerifier.create(flux.transform(processor()))
                .then(() -> publisher.emit("hi", "hello"))
                .expectNext("HI:2")
                .expectNext("HELLO:5")
                .expectComplete()
                .verify();
    }

    @Test
    public void publisherTest3() {
        var publisher = TestPublisher.<String>create();
        var flux = publisher.flux();

        // passes because processor() doesn't allow single letter items

        StepVerifier.create(flux.transform(processor()))
                .then(() -> publisher.emit("a", "b", "c"))
                .expectComplete()
                .verify();
    }
}
