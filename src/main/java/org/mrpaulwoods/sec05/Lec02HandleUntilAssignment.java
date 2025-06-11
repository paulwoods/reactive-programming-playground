package org.mrpaulwoods.sec05;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {

    // redo Lec07FluxGenerateUntil using handle
    public static void main(String[] args) {

        Flux.<String>generate(sink -> sink.next(Util.faker().country().name()))
                .handle((country, sink) -> {
                    sink.next(country);
                    if (country.equalsIgnoreCase("Canada")) {
                        sink.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }

}
