package org.mrpaulwoods.sec13.client;

import org.mrpaulwoods.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getBook() {
        return this.httpClient
                .get()
                .uri("/demo07/book")
                .responseContent()
                .asString()
                .next();
    }

    public Mono<String> getBookLimited() {
        return this.httpClient
                .get()
                .uri("/demo07/book")
                .responseContent()
                .asString()

                // start with the limit calls
                // if allowed, then limitCalls returns empty, and starts with will return mono from the api
                // if not allowed, then limitCalls throws ex, which propagates to subscriber
                .startWith(RateLimiter.limitCalls())

                // add category context
                .contextWrite(UserService.userCategoryContext())
                .next();
    }

}
