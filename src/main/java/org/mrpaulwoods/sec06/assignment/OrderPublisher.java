package org.mrpaulwoods.sec06.assignment;

import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public class OrderPublisher {

    // http://localhost:7070/demo04/orders/stream
    // Awesome Plastic Clock:Computers:40:5

    public Flux<Order> getOrderSteam() {

        var loopResources = LoopResources.create("vins", 1, true);

        return HttpClient
                .create()
                .runOn(loopResources)
                .baseUrl("http://localhost:7070")
                .get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(Order::create);

    }

}
