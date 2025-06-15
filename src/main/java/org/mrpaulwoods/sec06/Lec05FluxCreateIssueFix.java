package org.mrpaulwoods.sec06;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec04.helper.NameGenerator;
import reactor.core.publisher.Flux;

// fix Flux.create so it supports multiple subscribers
// adding .share()
public class Lec05FluxCreateIssueFix {

    public static void main(String[] args) {

        var generator = new NameGenerator();
        var flux = Flux.create(generator).share();
        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

        for (int i = 0; i < 10; i++) {
            generator.generate();
        }
    }

}
