package org.mrpaulwoods.sec10;

import org.mrpaulwoods.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec04Assignment {

    public static final AtomicInteger counter = new AtomicInteger(0);
    public static FileWriter writer;

    public static void main(String[] args) {

        // for each window, write log files, names log1.txt, log2.txt, ...

        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(Lec04Assignment::processEvents)
                .subscribe();

        Util.sleepSeconds(10);
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event-" + (i + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        return flux
                .doFirst(() -> {
                    counter.incrementAndGet();
                    System.out.println("startup " + counter.get());
                    try {
                        Path folder = Path.of(System.getProperty("java.io.tmpdir"), "sec10");
                        Files.createDirectories(folder);
                        Path filename = Path.of(folder.toString(), "log" + counter.get() + ".txt");
                        System.out.println("filename " + filename);
                        writer = new FileWriter(filename.toFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnNext(e -> {
                    System.out.print(counter.get());
                    try {
                        writer.write(e);
                        writer.write("\n");
                        writer.flush();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .doFinally(s -> {
                    System.out.println("complete " + counter.get());
                    try {
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .then();
    }

}
