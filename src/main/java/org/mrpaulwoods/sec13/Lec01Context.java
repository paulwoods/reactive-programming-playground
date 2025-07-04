package org.mrpaulwoods.sec13;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Lec01Context {

    private static final Logger log = LoggerFactory.getLogger(Lec01Context.class);

    public static void main(String[] args) {
//        demo1();
        demo2();
    }

    public static void demo1() {
        getWelcomeMessage1()
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage1() {
        return Mono
                .deferContextual(ctx -> {
                    log.info("context: {}", ctx);
                    return Mono.fromSupplier(() -> "Welcome ");

                });
    }


    public static void demo2() {
        getWelcomeMessage2()
                .subscribe(Util.subscriber());
        getWelcomeMessage2()
                .contextWrite(Context.of("user", "sam"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage2() {
        return Mono
                .deferContextual(ctx -> {
                    if (ctx.hasKey("user")) {
                        return Mono.fromSupplier(() -> "Welcome %s".formatted((String) ctx.get("user")));
                    }

                    return Mono.error(new RuntimeException("401 unauthenticated"));
                });
    }

}
