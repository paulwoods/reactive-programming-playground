package org.mrpaulwoods.sec02.file;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFileService implements FileService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LocalFileService.class);
    private static final Path PATH = Path.of("src/main/resources/sec02");

    @Override
    public Mono<String> read(String filename) {
        return Mono.fromCallable(() -> {
            log.info("reading file {}", filename);
            return Files.readString(PATH.resolve(filename));
        });
    }

    @Override
    public Mono<Void> write(String filename, String content) {
        return Mono.fromCallable(() -> {
            log.info("writing file {}", filename);
            try {
                Files.writeString(PATH.resolve(filename), content, StandardCharsets.UTF_8);
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Mono<Void> delete(String filename) {
        return Mono.fromCallable(() -> {
            log.info("deleting file {}", filename);
            Files.delete(PATH.resolve(filename));
            return null;
        });
    }

}
