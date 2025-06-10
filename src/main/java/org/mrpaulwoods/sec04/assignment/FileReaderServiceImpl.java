package org.mrpaulwoods.sec04.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReaderServiceImpl implements FileReaderService {

    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

    @Override
    public Flux<String> read(Path path) {

        return Flux.generate(
                () -> open(path),
                this::readLine,
                this::close
        );
    }

    BufferedReader open(Path path) {
        log.info("opening file {}", path);

        BufferedReader reader;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reader;
    }

    BufferedReader readLine(BufferedReader reader, SynchronousSink<String> sink) {
        log.info("reading line from file");

        try {
            String line = reader.readLine();

            log.info("read line: {}", line);

            if (line == null) {
                sink.complete();
            } else {
                sink.next(line);
            }

        } catch (IOException e) {
            sink.error(e);
        }

        return reader;
    }

    void close(BufferedReader reader) {
        log.info("closing file");

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
