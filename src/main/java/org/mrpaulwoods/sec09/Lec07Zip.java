package org.mrpaulwoods.sec09;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    - we will subscribe to all the producers at the same time
    - all or nothing
    - all producers will have to emit an item
 */
public class Lec07Zip {

    public static void main(String[] args) {
        Flux.zip(getBody(), getEngine(), getTires())
                .map(tuple -> new Car(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    public static Flux<String> getBody() {
        return Flux.range(1, 5)
                .map(i -> "Body-" + i)
                .delayElements(Duration.ofMillis(100));
    }

    public static Flux<String> getEngine() {
        return Flux.range(1, 3)
                .map(i -> "Engine-" + i)
                .delayElements(Duration.ofMillis(200));
    }

    public static Flux<String> getTires() {
        return Flux.range(1, 10)
                .map(i -> "Tire-" + i)
                .delayElements(Duration.ofMillis(75));
    }

    record Car(String body, String engine, String tires) {
    }

}
