package org.mrpaulwoods.sec06;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Hot - 1 data producer for all the subscribers.
    .share == .publish().refCount(1)
    It needs 1 min subscriber to emit data
    It stops when there are zero subscribers
    re-subscription - it starts again (at the beginning) when there is a new subscriber
    To have min 2 subscribers, use publish().refCount(2)
 */
public class Lec02HotPublisher {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec02HotPublisher.class);

    public static void main(String[] args) {

        var movieFlux = movieStream()
                .share();
//                .publish().refCount(2);

        Util.sleepSeconds(2);

        movieFlux
                .take(1)
                .subscribe(Util.subscriber("sam"));

        Util.sleepSeconds(3);

        movieFlux
                .take(3).subscribe(Util.subscriber("mike"));

        Util.sleepSeconds(15);
    }

    // movie theater
    private static Flux<String> movieStream() {
        return Flux.generate(
                        () -> {
                            log.info("received the request");
                            return 1;
                        },
                        (state, sink) -> {
                            var scene = "movie scene " + state;
                            log.info("playing {}", scene);
                            sink.next(scene);
                            return ++state;
                        }
                )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }

}
