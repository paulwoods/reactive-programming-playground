package org.mrpaulwoods.sec12;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Lec01SinkOne {

    public static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Lec01SinkOne.class);

    public static void main(String[] args) {
        demo3();
    }

    // send a value
    private static void demo1() {
        Sinks.One<Object> sink = Sinks.one();
        Mono<Object> mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitValue("hi");
    }

    //send "i do not have data", and the complete signal
    private static void demo1b() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitEmpty();
    }

    // send an error
    private static void demo1c() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitError(new RuntimeException("Boom!")); // send "i do not have data"
    }

    // two subscribers - each gets one value
    private static void demo1d() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("mike"));
        sink.tryEmitValue("hi");
    }

    // you can emit before the subscribers subscribe
    private static void demo2() {
        var sink = Sinks.one();
        var mono = sink.asMono();
        sink.tryEmitValue("hi");
        mono.subscribe(Util.subscriber("sam"));
        mono.subscribe(Util.subscriber("mike"));
    }

    // tried to emit 2 values to a mono - so it fails on the 2nd value
    private static void demo3() {
        var sink = Sinks.one();
        var mono = sink.asMono();

        mono.subscribe(Util.subscriber("sam"));

        sink.emitValue("hi", (signalType, emitResult) -> {
            // no logs here, because this emit works
            log.info("hi {}", signalType.name());
            log.info("hi {}", emitResult.name());
            return false;
        });

        sink.emitValue("hello", (signalType, emitResult) -> {
            // this emit fails, so it throws
            log.info("hello {}", signalType.name());
            log.info("hello {}", emitResult.name());
            return false;
        });

    }

}
