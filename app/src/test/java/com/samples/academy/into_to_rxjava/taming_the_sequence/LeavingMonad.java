package com.samples.academy.into_to_rxjava.taming_the_sequence;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Created by abdellahselassi on 4/12/17.
 */

public class LeavingMonad {
    @Test
    public void forEach() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        values
                .take(5)
                .forEach(v -> System.out.println(v));
        System.out.println("Subscribed");
        Thread.sleep(1000);
    }

    @Test
    public void forEachWithError() throws InterruptedException {
        Observable<Long> values = Observable.error(new Exception("Oops"));

        try {
            values
                    .take(5)
                    .toBlocking()
                    .forEach(v -> System.out.println(v));
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }
        System.out.println("Subscribed");
    }

    @Test
    public void first() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        long value = values
                .take(5)
                .toBlocking()
                .first(i -> i > 2);
        System.out.println(value);
    }

    @Test
    public void toIterable() {
        Observable<Long> values = Observable.interval(500, TimeUnit.MILLISECONDS);

        Iterable<Long> iterable = values.take(5).toBlocking().toIterable();
        for (long l : iterable) {
            System.out.println(l);
        }
    }

    @Test
    public void next() throws InterruptedException {
        Observable<Long> values = Observable.interval(500, TimeUnit.MILLISECONDS);

        values.take(5)
                .subscribe(v -> System.out.println("Emitted: " + v));

        Iterable<Long> iterable = values.take(5).toBlocking().next();
        for (long l : iterable) {
            System.out.println(l);
            Thread.sleep(750);
        }
    }

    @Test
    public void latest() throws InterruptedException {
        Observable<Long> values = Observable.interval(500, TimeUnit.MILLISECONDS);

        values.take(5)
                .subscribe(v -> System.out.println("Emitted: " + v));

        Iterable<Long> iterable = values.take(5).toBlocking().latest();
        for (long l : iterable) {
            System.out.println(l);
            Thread.sleep(750);
        }
    }

    @Test
    public void mostRecent() throws InterruptedException {
        Observable<Long> values = Observable.interval(500, TimeUnit.MILLISECONDS);

        values.take(5)
                .subscribe(v -> System.out.println("Emitted: " + v));

        Iterable<Long> iterable = values.take(5).toBlocking().mostRecent(-1L);
        for (long l : iterable) {
            System.out.println(l);
            Thread.sleep(400);
        }
    }

    @Test
    public void future() throws InterruptedException {
        Observable<Long> values = Observable.interval(500, TimeUnit.MILLISECONDS);

        values.take(5)
                .subscribe(v -> System.out.println("Emitted: " + v));

        Iterable<Long> iterable = values.take(5).toBlocking().mostRecent(-1L);
        for (long l : iterable) {
            System.out.println(l);
            Thread.sleep(400);
        }
    }

    @Test
    public void locks() {
        ReplaySubject<Integer> subject = ReplaySubject.create();

        subject.toBlocking().forEach(v -> System.out.println(v));
        subject.onNext(1);
        subject.onNext(2);
        subject.onCompleted();
    }

}
