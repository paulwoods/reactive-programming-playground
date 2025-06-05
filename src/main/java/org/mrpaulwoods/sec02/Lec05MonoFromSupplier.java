package org.mrpaulwoods.sec02;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec05MonoFromSupplier {

    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    public static void main(String[] args) {

        var list = List.of(1, 2, 3);

        Mono.just(sum(list));
//                .subscribe(Util.subscriber());

        // lazy - doesn't run sum unless requested
        Mono.fromSupplier(() -> sum(list))
                .subscribe(Util.subscriber());

    }

    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);
        return list.stream().reduce(0, Integer::sum);
    }

}
