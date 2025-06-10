package org.mrpaulwoods.sec04;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec08GenerateWithState {

    public static void main(String[] args) {

        // stop when you emit Canada or 10 countries

        Flux.generate(
                () -> 0,
                (counter, sink) -> {
                    var country = Util.faker().country().name();
                    sink.next(country);
                    counter++;
                    if (country.equalsIgnoreCase("Canada") || counter == 10) {
                        sink.complete();
                    }
                    return counter;
                }
        ).subscribe(Util.subscriber());

    }

}
