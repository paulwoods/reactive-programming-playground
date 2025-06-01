package org.mrpaulwoods.sec01;

import org.mrpaulwoods.sec01.publisher.PublisherImpl;
import org.mrpaulwoods.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/*
    1. publisher does not produce data unless the subscriber requests for it
    2. publisher will produce only <= subscriber requested items, publisher can also produce 0 items.
    3. subscriber can cancel the subscription. producer should stop at that moment as the subscriber has no longer
        interested in consuming the data.
    4. producer can send the error signal to indicate something is wrong.
 */
public class Demo {

    private final static Logger log = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("1. ###############");
        demo1();
        log.info("2. ###############");
        demo2();
        log.info("3. ###############");
        demo3();
        log.info("4. ###############");
        demo4();
    }

    public static void demo1() {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
    }

    public static void demo2() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
    }

    public static void demo3() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
    }

    public static void demo4() throws InterruptedException {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(11);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
    }

}
