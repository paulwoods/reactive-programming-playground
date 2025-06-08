package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec03FluxFromIterableOrArray {

    public static void main(String[] args) {

        var list = List.of("a", "b", "c");

        Flux.fromIterable(list)
                .subscribe(Util.subscriber());

        Integer[] array = {1, 2, 3, 4, 5, 6};

        Flux.fromArray(array)
                .subscribe(Util.subscriber());

    }

}
