package org.mrpaulwoods.sec03;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec13FluxMono {

    public static void main(String[] args) {

        // mono -> flux
        var mono1 = getUsername(1);
        save(Flux.from(mono1));

        // flux -> mono
//        var flux1 = Flux.range(1, 10);
//        flux1.next()
//                .subscribe(Util.subscriber());

        var flux2 = Flux.range(1, 10);
        Mono.from(flux2)
                .subscribe(Util.subscriber());

    }

    private static void monoToFlux() {
        var mono = getUsername(1);
        var flux = Flux.from(mono);
        save(flux);
    }


    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }

    private static void save(Flux<String> flux) {
        flux.subscribe(Util.subscriber());
    }


}
