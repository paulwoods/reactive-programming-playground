package org.mrpaulwoods.sec12;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec05MulticastDirectBestEffort {
    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec05MulticastDirectBestEffort.class);

    public static void main(String[] args) {
        demo4();
    }

    // if one subscribe is slow, and the buffer fills, all subscribers will loose messages
    private static void demo1() {

        // set the buffer to 16 messages
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 0; i < 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleepSeconds(10);
    }

    // solution1: increase buffer size
    // but sam (fast) is delayed by slow mike
    private static void demo2() {

        // set the buffer to 16 messages
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().onBackpressureBuffer(100);
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 0; i < 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleepSeconds(40);
    }

    // sam get all the messages, as he is faster
    // mike only gets one message
    private static void demo3() {

        // set the buffer to 16 messages
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().directBestEffort();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 0; i < 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleepSeconds(40);
    }

    // setup onBackpressureBuffer for mike
    // sam gets all of the messages quickly
    // mike gets the messages slowly
    private static void demo4() {

        // set the buffer to 16 messages
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().directBestEffort();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.onBackpressureBuffer().delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 0; i < 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleepSeconds(40);
    }

}
