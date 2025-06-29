package org.mrpaulwoods.sec10.assignment;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Lec02BufferAssignment {

    public static final Set<String> REPORTED_CATEGORIES = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");
    public static final Integer BOOK_CREATE_INTERVAL_MS = 200;
    public static final Integer BUFFER_INTERVAL_MS = 5_000;

    public static void main(String[] args) {

        Flux.interval(Duration.ofMillis(BOOK_CREATE_INTERVAL_MS))
                .map(Lec02BufferAssignment::createBookOrder)
                .filter(Lec02BufferAssignment::filterBooks)
                .buffer(Duration.ofMillis(BUFFER_INTERVAL_MS))
                .map(Lec02BufferAssignment::createReport)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(50);
    }

    private static BookOrder createBookOrder(Long aLong) {
        return BookOrder.create();
    }

    private static boolean filterBooks(BookOrder bookOrder) {
        return REPORTED_CATEGORIES.contains(bookOrder.genre());
    }

    private static RevenueReport createReport(List<BookOrder> bookOrders) {

        var revenue = bookOrders.stream()
                .collect(Collectors.groupingBy(
                        BookOrder::genre,
                        Collectors.summingInt(BookOrder::price)
                ));

        return new RevenueReport(OffsetDateTime.now(), revenue);
    }

}
