package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {

    public static void main(String[] args) {
        var list = List.of(1, 2, 3, 4);
        var stream = list.stream();

        // doesn't work because stream is closed before sub2 starts
//        var flux = Flux.fromStream(stream);
//        flux.subscribe(Util.subscriber("sub1"));
//        flux.subscribe(Util.subscriber("sub2"));

        var flux = Flux.fromStream(list::stream);
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

    }

}
