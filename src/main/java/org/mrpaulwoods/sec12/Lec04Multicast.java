package org.mrpaulwoods.sec12;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Sinks;

public class Lec04Multicast {

    public static void main(String[] args) {
        demo2();
    }

    // late subscribers do not see the old messages
    private static void demo1() {

        // onBackPressureBuffer - bounded queue (256 by default)
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("new message");
    }

    // warmup
    // emit messages before anyone subscribes
    // first subscriber gets the messages. the others don't get any messages
    // messages are stored in the bounded queue
    private static void demo2() {

        // onBackPressureBuffer - bounded queue (256 by default)
        var sink = Sinks.many().multicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        sink.tryEmitNext("hi");
        sink.tryEmitNext("how are you");
        sink.tryEmitNext("?");
        sink.tryEmitNext("?");
        sink.tryEmitNext("?");

        Util.sleepSeconds(2);

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("new message");
    }

}
