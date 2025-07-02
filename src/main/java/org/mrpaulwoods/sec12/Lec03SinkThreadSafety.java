package org.mrpaulwoods.sec12;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Lec03SinkThreadSafety {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec03SinkThreadSafety.class);

    public static void main(String[] args) {
        demo2();
    }

    // sink is not synchronized if multiple threads are emitting to the sink
    private static void demo1() {

        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.tryEmitNext(j);
            });
        }

        Util.sleepSeconds(2);
        log.info("list size: {}", list.size());
    }

    // use retry if there is an error due to thread safety
    private static void demo2() {

        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext(j, ((signalType, emitResult) -> {

                    // retry if the error is FAIL_NON_SERIALIZED
                    return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
                }));
            });
        }

        Util.sleepSeconds(2);
        log.info("list size: {}", list.size());
    }

}
