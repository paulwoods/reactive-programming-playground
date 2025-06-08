package org.mrpaulwoods.sec02;

import org.mrpaulwoods.common.Util;
import org.mrpaulwoods.sec02.file.LocalFileService;

public class Lec12Assignment {

    public static void main(String[] args) {
        var fileService = new LocalFileService();

        fileService.write("filename.txt", "Hello World!");

        fileService.read("filename.txt")
                .subscribe(Util.subscriber());

        fileService.delete("filename.txt")
                .subscribe(Util.subscriber());

    }
}
