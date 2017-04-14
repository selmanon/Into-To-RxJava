package com.samples.academy.into_to_rxjava.sequence_basics;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;

/**
 * Created by abdellahselassi on 4/14/17.
 */

public class Inspection {
    @Test
    public void all() {
        Observable<Integer> values = Observable.create(o -> {
            o.onNext(0);
            o.onNext(10);
            o.onNext(10);
            o.onNext(2);
            o.onCompleted();
        });


        Subscription evenNumbers = values
                .all(i -> i % 2 == 0)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
        evenNumbers.unsubscribe();
    }

    @Test
    public void exists() {
        Observable<Integer> values = Observable.range(0, 2);

        Subscription subscription = values
                .exists(i -> i > 2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
        subscription.unsubscribe();
    }

    @Test
    public void isEmpty() {
        Observable<Long> values = Observable.timer(1, TimeUnit.MILLISECONDS);

        Subscription subscription = values
                .isEmpty()
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );

    }

    @Test
    public void contains() throws InterruptedException {
        Observable<Long> values = Observable.interval(1, TimeUnit.MILLISECONDS);

        Subscription subscription = values
                .contains(4L)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
        Thread.sleep(10);
    }

    @Test
    public void defaultIfEmpty() {
        Observable<Integer> values = Observable.empty();

        Subscription subscription = values
                .defaultIfEmpty(2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void elementAt() {
        Observable<Integer> values = Observable.range(100, 10);

        Subscription subscription = values
                .elementAt(2)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void sequenceEqual() {
        Observable<Integer> values = Observable.create(o -> {
            o.onNext(1);
            o.onNext(2);
            o.onCompleted();
        });

        Observable.sequenceEqual(values, values)
                .subscribe(
                        v -> System.out.println(v),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );

    }
}
