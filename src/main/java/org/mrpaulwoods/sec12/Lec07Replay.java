package org.mrpaulwoods.sec12;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec07Replay {

    public static void main(String[] args) {
        demo3();
    }

    // stores/replays all messages in an unbounded queue
    private static void demo1() {

        // onBackPressureBuffer - bounded queue (256 by default)
        var sink = Sinks.many().replay().all();
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

    // only replay the last 1 message (.limit(1))
    private static void demo2() {

        var sink = Sinks.many().replay().limit(1);
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

    // only replay the last 1 message (.limit(1))
    private static void demo3() {

        var sink = Sinks.many().replay().limit(Duration.ofMillis(1500));
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));

        sink.tryEmitNext("message 1");
        Util.sleepSeconds(1);
        sink.tryEmitNext("message 2");
        Util.sleepSeconds(1);
        sink.tryEmitNext("message 3");
        Util.sleepSeconds(1);

        flux.subscribe(Util.subscriber("jake"));

        sink.tryEmitNext("new message");
    }

}
