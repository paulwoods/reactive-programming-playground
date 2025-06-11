package org.mrpaulwoods.sec05;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {

    public static void main(String[] args) {

        /*
            Requirement:
            1 => -2
            4 => do not send
            7 => error
            else => send as is
         */

        Flux
                .range(1, 10)
                .handle((item, sink) -> {
                    switch (item) {
                        case 1 -> sink.next(-2);
                        case 4 -> {
                        }
                        case 7 -> sink.error(new RuntimeException("Boom!"));
                        default -> sink.next(item);
                    }
                })
                .subscribe(Util.subscriber());
    }

}
