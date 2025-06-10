package org.mrpaulwoods.sec04.assignment;

import org.mrpaulwoods.common.Util;

import java.nio.file.Path;

public class Sec04Assignment {

    private static final Path PATH = Path.of("src/main/resources/sec04/testfile.txt");

    public static void main(String[] args) {

        var generator = new FileReaderServiceImpl();

        generator.read(PATH)
                .subscribe(Util.subscriber());

    }

}
