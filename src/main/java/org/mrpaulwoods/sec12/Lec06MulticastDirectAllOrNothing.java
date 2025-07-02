package org.mrpaulwoods.sec12;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec06MulticastDirectAllOrNothing {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec06MulticastDirectAllOrNothing.class);

    public static void main(String[] args) {
        demo1();
    }

    // if one subscriber is slow, don't give messages to anyone
    private static void demo1() {

        // set the buffer to 16 messages
        System.setProperty("reactor.bufferSize.small", "16");

        var sink = Sinks.many().multicast().directAllOrNothing();
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("mike"));

        for (int i = 0; i < 100; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

        Util.sleepSeconds(40);
    }


}
