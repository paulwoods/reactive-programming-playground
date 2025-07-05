package org.mrpaulwoods.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

public class Lec05AssertNextTest {

    private Flux<Book> getBooks() {
        return Flux.range(1, 3)
                .map(i -> new Book(
                        i,
                        Util.faker().book().author(),
                        Util.faker().book().title()
                ));
    }

    @Test
    public void assertNextTest() {
        StepVerifier.create(getBooks())
                // assert 1st object has the id of 1
                // assert the rest of the objects have a title

                .assertNext(b -> Assertions.assertEquals(1, b.id()))
                .thenConsumeWhile(b -> Objects.nonNull(b.title()))
                .expectComplete()
                .verify();
    }

    @Test
    public void collectAllAndTest() {
        StepVerifier.create(getBooks().collectList())
                .assertNext(list -> Assertions.assertEquals(3, list.size()))
                .expectComplete()
                .verify();
    }

    record Book(int id, String author, String title) {
    }

}
