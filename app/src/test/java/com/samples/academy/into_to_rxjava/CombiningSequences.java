package com.samples.academy.into_to_rxjava;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by abdellahselassi on 4/13/17.
 */

public class CombiningSequences {

    @After
    public void after() throws InterruptedException {
        Thread.sleep(100);
    }

    @Test
    public void concat() {
        Observable<Integer> seqOne = Observable.range(0, 3);
        Observable<Integer> seqTwo = Observable.range(10, 3);

        Observable.concat(seqOne, seqTwo).subscribe(System.out::println);
    }

    @Test
    public void concatWithGroupBy() {
        Observable<String> words = Observable.just("First", "Second", "Third", "Fourth", "Fifth", "Sixth");

        Observable.concat(words.groupBy(character -> character.charAt(0))).subscribe(System.out::println);
    }

    @Test
    public void concatWith() {
        Observable<Integer> seq1 = Observable.range(0, 3);
        Observable<Integer> seq2 = Observable.range(10, 3);
        Observable<Integer> seq3 = Observable.just(20);

        seq1.concatWith(seq2)
                .concatWith(seq3)
                .subscribe(System.out::println);
    }

    @Test
    public void repeat() {
        Observable<Integer> words = Observable.range(0, 2);

        words.repeat(2)
                .subscribe(System.out::println);
    }

    @Test
    public void repeatWhen() {
        Observable<Integer> values = Observable.range(0, 5);

        values
                .take(2)
                .repeatWhen(observable -> observable.take(2))
                .subscribe(new PrintSubscriber("repeatWhen"));
    }

    @Test
    public void startWith() {
        Observable<Integer> values = Observable.range(0, 3);

        values.startWith(-1, -2)
                .subscribe(System.out::println);
    }

    @Test
    public void amb() {
        Observable.amb(
                Observable.timer(100, TimeUnit.MILLISECONDS).map(i -> "First"),
                Observable.timer(50, TimeUnit.MILLISECONDS).map(i -> "Second"))

                .subscribe(System.out::println);
    }

    @Test
    public void ambWith() {
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .map(i -> "First")
                .ambWith(Observable.timer(50, TimeUnit.MILLISECONDS).map(i -> "Second"))
                .ambWith(Observable.timer(70, TimeUnit.MILLISECONDS).map(i -> "Third"))
                .subscribe(System.out::println);
    }

    @Test
    public void merge() throws InterruptedException {
        Observable.merge(
                Observable.interval(250, TimeUnit.MILLISECONDS).map(i -> "First"),
                Observable.interval(150, TimeUnit.MILLISECONDS).map(i -> "Second"))
                .take(10)
                .subscribe(System.out::println);
        Thread.sleep(1500);
    }

    @Test
    public void mergeWith() throws InterruptedException {
        Observable.interval(250, TimeUnit.MILLISECONDS)
                .map(i -> "First")
                .mergeWith
                        (Observable.interval(150, TimeUnit.MILLISECONDS)
                                .map(i -> "Second"))
                .take(10)
                .subscribe(System.out::println);
        Thread.sleep(2500);
    }

    @Test
    public void mergeDelayError() throws InterruptedException {
        Observable<Long> failAt200 = Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(2),
                Observable.error(new Exception("Exception : failAt200"))
        );

        Observable<Long> completeAt400 = Observable
                .interval(100, TimeUnit.MILLISECONDS)
                .take(4);

        Observable.mergeDelayError(failAt200, completeAt400).subscribe(System.out::println, System.out::println);
        Thread.sleep(500);
    }

    @Test
    public void mergeDelayCompositeError() throws InterruptedException {
        Observable<Long> failAt200 = Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(2),
                Observable.error(new Exception("Exception : failAt200"))
        );

        Observable<Long> failAt300 = Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
                Observable.error(new Exception("Exception : failAt300"))
        );


        Observable<Long> completeAt400 = Observable
                .interval(100, TimeUnit.MILLISECONDS)
                .take(4);

        Observable.mergeDelayError(failAt200, failAt300, completeAt400).subscribe(System.out::println, System.out::println);

        Thread.sleep(1000);
    }

    @Test
    public void switchOnNext() throws InterruptedException {
        Observable.switchOnNext(
                Observable.interval(100, TimeUnit.MILLISECONDS)
                        .map(
                                i -> Observable.interval(30, TimeUnit.MILLISECONDS)
                                        .map(i2 -> i)
                        )
        ).take(9).subscribe(System.out::println);

        Thread.sleep(900);
    }

    @Test
    public void switchMap() throws InterruptedException {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .switchMap(
                        i -> Observable.interval(30, TimeUnit.MILLISECONDS)
                                .map(l -> i)
                )
                .take(9)
                .subscribe(System.out::println);

        Thread.sleep(900);
    }

    @Test
    public void zip() throws InterruptedException {
        Observable.zip(
                Observable.interval(100, TimeUnit.MILLISECONDS)
                        .doOnNext(i -> System.out.println("Left emits " + i)),
                Observable.interval(150, TimeUnit.MILLISECONDS)
                        .doOnNext(i -> System.out.println("Right emits " + i)),
                (i1, i2) -> i1 + " - " + i2)
                .take(6)
                .subscribe(System.out::println);

        Thread.sleep(900);
    }

    @Test
    public void zipMoreThanTwo() throws InterruptedException {
        Observable.zip(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                Observable.interval(150, TimeUnit.MILLISECONDS),
                Observable.interval(050, TimeUnit.MILLISECONDS),
                (i1, i2, i3) -> i1 + " - " + i2 + " - " + i3)
                .take(6)
                .subscribe(System.out::println);

        Thread.sleep(900);
    }

    @Test
    public void zipCount() {
        Observable.zip(
                Observable.range(0, 5),
                Observable.range(0, 3),
                Observable.range(0, 8),
                (i1, i2, i3) -> i1 + " - " + i2 + " - " + i3)
                .count()
                .subscribe(System.out::println);
    }

    @Test
    public void zipWith() throws InterruptedException {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .zipWith(
                        Observable.interval(150, TimeUnit.MILLISECONDS),
                        (i1, i2) -> i1 + " - " + i2)
                .take(6)
                .subscribe(System.out::println);

        Thread.sleep(900);
    }

    @Test
    public void combineLatest() throws InterruptedException {
        Observable.combineLatest(
                Observable.interval(100, TimeUnit.MILLISECONDS)
                        .doOnNext(i -> System.out.println("Left emits")),
                Observable.interval(150, TimeUnit.MILLISECONDS)
                        .doOnNext(i -> System.out.println("Right emits")),
                (i1, i2) -> i1 + " - " + i2
        )
                .take(6)
                .subscribe(System.out::println);

        Thread.sleep(900);
    }
}
