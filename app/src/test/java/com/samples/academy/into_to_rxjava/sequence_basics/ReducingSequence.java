package com.samples.academy.into_to_rxjava.sequence_basics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by abdellahselassi on 4/14/17.
 */

public class ReducingSequence {

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
    public void filter() {
        Observable<Integer> values = Observable.range(0, 10);
        subscription = values
                .filter(v -> v % 2 == 0)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void distinct() {
        Observable<Integer> values = Observable.create(o -> {
            o.onNext(1);
            o.onNext(1);
            o.onNext(2);
            o.onNext(3);
            o.onNext(2);
            o.onCompleted();
        });

        subscription = values
                .distinct()
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );

    }

    @Test
    public void distinctByFirstChar() {
        Observable<String> values = Observable.create(o -> {
            o.onNext("First");
            o.onNext("Second");
            o.onNext("Third");
            o.onNext("Fourth");
            o.onNext("Fifth");
            o.onCompleted();
        });

        subscription = values
                .distinct(v -> v.charAt(0))
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void distinctUntilChanged() {
        Observable<Integer> values = Observable.create(o -> {
            o.onNext(1);
            o.onNext(1);
            o.onNext(2);
            o.onNext(3);
            o.onNext(2);
            o.onCompleted();
        });

        subscription = values
                .distinctUntilChanged()
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void ignoreElements() {
        Observable<Integer> values = Observable.range(0, 10);

        subscription = values
                .ignoreElements()
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void skip() {
        Observable<Integer> values = Observable.range(0, 5);

        subscription = values
                .skip(2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );

    }

    @Test
    public void take() {
        Observable<Integer> values = Observable.range(0, 5);

        subscription = values
                .take(2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void skipWhile() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        subscription = values
                .skipWhile(v -> v < 2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );

        Thread.sleep(400);
    }

    @Test
    public void takeWhile() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        subscription = values
                .takeWhile(v -> v < 2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );

        Thread.sleep(400);
    }

    @Test
    public void skipLast() {
        Observable<Integer> values = Observable.range(0, 5);

        subscription = values
                .skipLast(2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void takeLast() {
        Observable<Integer> values = Observable.range(0, 5);

        subscription = values
                .takeLast(2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void takeUntil() throws InterruptedException {
        Observable<Long> values = Observable.interval(100,TimeUnit.MILLISECONDS);
        Observable<Long> cutoff = Observable.timer(250, TimeUnit.MILLISECONDS);

        subscription = values
                .takeUntil(cutoff)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
        Thread.sleep(400);
    }

    @Test
    public void skipUntil() throws InterruptedException {
        Observable<Long> values = Observable.interval(100,TimeUnit.MILLISECONDS);
        Observable<Long> cutoff = Observable.timer(250, TimeUnit.MILLISECONDS);

        subscription = values
                .skipUntil(cutoff)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
        Thread.sleep(2000);
    }
}
