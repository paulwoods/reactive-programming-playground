package org.mrpaulwoods.sec13;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class Lec03ContextPropagation {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec03ContextPropagation.class);

    public static void main(String[] args) {
        demo1();

        Util.sleepSeconds(2);
    }

    private static void demo1() {

        //change the context only for producer2
        getWelcomeMessage()
                .concatWith(Flux.merge(
                        producer1(),
                        producer2().contextWrite(ctx -> Context.empty()),
                        producer3()))
                .contextWrite(Context.of("user", "sam"))
                .subscribe(Util.subscriber());

    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")) {
                return Mono.fromSupplier(() -> "Welcome %s".formatted((String) ctx.get("user")));
            }

            return Mono.error(new RuntimeException("401 unauthenticated"));
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer1: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer2: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.parallel());
    }

    private static Mono<String> producer3() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer3: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.parallel());
    }

}
