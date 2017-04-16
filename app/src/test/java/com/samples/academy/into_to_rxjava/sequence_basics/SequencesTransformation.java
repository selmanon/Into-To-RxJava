package com.samples.academy.into_to_rxjava.sequence_basics;

import com.samples.academy.into_to_rxjava.PrintSubscriber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by abdellahselassi on 4/14/17.
 */

public class SequencesTransformation {
    @Test
    public void map() {
        Observable<Integer> values = Observable.range(0, 4);

        values
                .map(i -> i + 1000)
                .subscribe(new PrintSubscriber("Map"));
    }

    @Test
    public void cast() {
        Observable<Object> values = Observable.just(0, 1, 2, 3);

        values
                .cast(Integer.class)
                .subscribe(new PrintSubscriber("Map"));
    }

    @Test
    public void ofType() {
        Observable<Object> values = Observable.just(0, 1, "2", 3);

        values
                .ofType(Integer.class)
                .subscribe(new PrintSubscriber("Map"));
    }

    @Test
    public void timestamp() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        values.take(3)
                .timestamp()
                .subscribe(new PrintSubscriber("Timestamp"));

        Thread.sleep(500);
    }

    @Test
    public void timeInterval() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        values.take(3)
                .timeInterval()
                .subscribe(new PrintSubscriber("TimeInterval"));

        Thread.sleep(500);
    }

    @Test
    public void materialize() {
        Observable<Integer> values = Observable.range(10, 5);

        values
                .take(3)
                .materialize()
                .subscribe(new PrintSubscriber("Materialize"));
    }

    @Test
    public void flatMap() {
        Observable<Integer> values = Observable.just(2);

        values
                .flatMap(i -> Observable.range(0, i))
                .subscribe(new PrintSubscriber("flatMap"));
    }

    @Test
    public void concatMap() throws InterruptedException {
        Observable.just(100, 150)
                .concatMap(i ->
                        Observable.interval(i, TimeUnit.MILLISECONDS)
                                .map(v -> i)
                                .take(3))
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("Completed"));

        Thread.sleep(1000);
    }

    @Test
    public void flatMapIterable() {
        Observable.range(1, 3)
                .flatMapIterable(i -> range(1, i))
                .subscribe(System.out::println);
    }

    public static Iterable<Integer> range(int start, int count) {
        List<Integer> list = new ArrayList<>();
        for (int i=start ; i<start+count ; i++) {
            list.add(i);
        }
        return list;
    }


}
