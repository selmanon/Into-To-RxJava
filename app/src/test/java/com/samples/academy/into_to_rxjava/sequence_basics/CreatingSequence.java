package com.samples.academy.into_to_rxjava.sequence_basics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by abdellahselassi on 4/13/17.
 */

public class CreatingSequence {
    private Subscription subscription;

    @Before
    public void before() {
        subscription = Subscriptions.empty();
    }

    @After
    public void after() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Test
    public void just() {
        Observable<String> words = Observable.just("one", "two", "three");
        subscription = words.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void empty() {
        Observable<String> values = Observable.empty();
        subscription = values.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void never() {
        Observable<String> values = Observable.never();
        subscription = values.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void error() {
        Observable<String> values = Observable.error(new Exception("Oops"));
        subscription = values.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void defer() throws InterruptedException {
        Observable<Long> now = Observable.defer(() ->
                Observable.just(System.currentTimeMillis()));

        now.subscribe(System.out::println);
        now.subscribe(System.out::println);
    }

    @Test
    public void create() {
        Observable<String> values = Observable.create(o -> {
            o.onNext("Hello");
            o.onCompleted();
        });
        subscription = values.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void interval() throws IOException, InterruptedException {
        Observable<Long> values = Observable.interval(1000, TimeUnit.MILLISECONDS);
        subscription = values.subscribe(
                v -> System.out.println("Received: " + v + Thread.currentThread()), // Thread.currentThread() is used as blocking Thread
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
        Thread.sleep(2000);
        subscription.unsubscribe();
    }

    @Test
    public void timer() throws InterruptedException {
        Observable<Long> values = Observable.timer(1, TimeUnit.SECONDS);
        subscription = values.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
        Thread.sleep(1000);
    }




}
