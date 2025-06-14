package org.mrpaulwoods.sec05.assignment;

import org.mrpaulwoods.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ExternalServiceClient extends AbstractHttpClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalServiceClient.class);

    private final Duration duration = Duration.ofSeconds(2);

    public Flux<String> getProduct(int id) {
        log.info("getting product {}", id);
        return normal(id)
                .timeout(duration, fallback(id))
                .switchIfEmpty(empty(id))
                .onErrorResume(RuntimeException.class, ex -> empty(id))
                ;
    }

    Flux<String> normal(int id) {
        return this.httpClient.get()
                .uri("/demo03/product/" + id)
                .responseContent()
                .asString();
    }

    Flux<String> fallback(int id) {
        return this.httpClient.get()
                .uri("/demo03/timeout-fallback/product/" + id)
                .responseContent()
                .asString();
    }

    Flux<String> empty(int id) {
        return this.httpClient.get()
                .uri("/demo03/empty-fallback/product/" + id)
                .responseContent()
                .asString();
    }

}
