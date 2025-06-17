package org.mrpaulwoods.sec07;

import org.mrpaulwoods.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Lec08Parallel {

    private static final Logger log = LoggerFactory.getLogger(Lec08Parallel.class);

    public static void main(String[] args) {

        Flux.range(1, 20)

                // parallel on 2 threads
                .parallel(16)

                // run slow tasks in parallel
                .runOn(Schedulers.parallel())
                .map(Lec08Parallel::process)

                // run on one thread
                .sequential()
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static int process(int i) {
        log.info("time consuming task {}", i);
        Util.sleepSeconds(1);
        return i * 2;
    }
}
