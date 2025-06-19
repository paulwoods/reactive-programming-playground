package org.mrpaulwoods.sec09.assignment;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public class ExternalServiceClient {

    private static final String BASE_URL = "http://localhost:7070";

    protected final HttpClient httpClient;

    public ExternalServiceClient() {
        var loopResources = LoopResources.create("vins", 1, true);
        this.httpClient = HttpClient
                .create()
                .runOn(loopResources)
                .baseUrl(BASE_URL);
    }

    public Mono<Item> getItem(int productId) {
        return Mono.zip(
                        getProduct(productId),
                        getPrice(productId),
                        getReview(productId)
                )
                .map(t -> new Item(t.getT1(), t.getT2(), t.getT3()));
    }

    public Mono<String> getProduct(int productId) {
        return this.httpClient.get()
                .uri("/demo05/product/" + productId)
                .responseContent()
                .asString()
                .next();
    }

    public Mono<String> getPrice(int productId) {
        return this.httpClient.get()
                .uri("/demo05/price/" + productId)
                .responseContent()
                .asString()
                .next();
    }

    public Mono<String> getReview(int productId) {
        return this.httpClient.get()
                .uri("/demo05/review/" + productId)
                .responseContent()
                .asString()
                .next();
    }
}

