package org.mrpaulwoods.sec13;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Lec02ContextAppendUpdate {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec02ContextAppendUpdate.class);

    public static void main(String[] args) {
        demo2();
    }

    // append additional context
    private static void demo1() {
        getWelcomeMessage()
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f"))
                .contextWrite(Context.of("user", "sam"))
                .subscribe(Util.subscriber());
    }

    // modify/replace existing context
    // remember - the top "contextWrite" overwrites/modified contextWrite below itself
    private static void demo2() {
        getWelcomeMessage()
                .contextWrite(ctx -> ctx.put("user", ctx.getOrDefault("user", "").toUpperCase()))
                .contextWrite(ctx -> Context.of("user", "mike")) // function replaces context
//                .contextWrite(ctx -> Context.empty())
                .contextWrite(Context.of("a", "b").put("c", "d").put("e", "f")) // append/modify context
                .contextWrite(Context.of("user", "sam"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("context: {}", ctx);
            if (ctx.hasKey("user")) {
                return Mono.fromSupplier(() -> "Welcome %s".formatted((String) ctx.get("user")));
            }

            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }

}
