package com.samples.academy.into_to_rxjava;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by abdellahselassi on 4/12/17.
 */

public class TimeShiftedSequences {

    @After
    public void after() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    public void bufferByCount() {
        Observable.range(0, 10)
                .buffer(4)
                .subscribe(System.out::println);
    }

    @Test
    public void bufferByTime() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(250, TimeUnit.MILLISECONDS, 2)
                .subscribe(System.out::println);
    }

    @Test
    public void bufferByCountAndTime() {
        Observable.interval(100, TimeUnit.MILLISECONDS).take(10)
                .buffer(250, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
    }

    @Test
    public void bufferWithSignal() {
        Observable.interval(100, TimeUnit.MILLISECONDS).take(10)
                .buffer(Observable.interval(250, TimeUnit.MILLISECONDS))
                .subscribe(System.out::println);
    }

    @Test
    public void bufferByCountOverlap() {
        Observable.range(0, 10)
                /*
                - When count > skip, the buffers overlap
                - When count < skip, elements are left out
                 */
                .buffer(2 /* Count */, 3 /* Skip */)
                .subscribe(System.out::println);
    }

    @Test
    public void bufferByTimeOverlap() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                /*
                - When timespan > timeshift, the buffers overlap
                - When timespan < timeshift, elements are left out
                 */
                .buffer(350, 200, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

    }

    @Test
    public void bufferBySignal() {
        Observable.interval(100, TimeUnit.MILLISECONDS).take(10)
                .buffer(
                        Observable.interval(250, TimeUnit.MILLISECONDS),
                        i -> Observable.timer(200, TimeUnit.MILLISECONDS))
                .subscribe(System.out::println);
    }

    @Test
    public void takeLastBuffer() {
        Observable.range(0, 5)
                .takeLastBuffer(2)
                .subscribe(System.out::println);
    }

    @Test
    public void takeLastBufferWithTime() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(5)
                .takeLast(200, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);
    }

    @Test
    public void delay() {
        Observable.interval(100, TimeUnit.MILLISECONDS).take(5)
                .delay(1, TimeUnit.SECONDS)
                .timeInterval()
                .subscribe(System.out::println);
    }

    @Test
    public void delayEachValue() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(5)
                .delay(i -> Observable.timer(i * 100, TimeUnit.MILLISECONDS))
                .timeInterval()
                .subscribe(System.out::println);
    }

    @Test
    public void delaySubscription() {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(5)
                .delaySubscription(1000, TimeUnit.MILLISECONDS)
                .timeInterval()
                .subscribe(System.out::println);
    }

    @Test
    public void sample() {
        Observable.interval(150, TimeUnit.MILLISECONDS)
                .sample(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
    }

    @Test
    public void throttleFirst() {
        Observable.interval(150, TimeUnit.MILLISECONDS)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
    }

    @Test
    public void throttleLast() {
        Observable.interval(150, TimeUnit.MILLISECONDS)
                .throttleLast(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
    }

    @Test
    public void debounce() throws InterruptedException {
        Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(500, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3)
        )
                .scan(0, (acc, v) -> acc + 1)
                //.debounce(150, TimeUnit.MILLISECONDS)
                .debounce(150, TimeUnit.MILLISECONDS)
                //.timeInterval()
                .subscribe(System.out::println);
        Thread.sleep(100);
    }

    @Test
    public void timeout() {
        Observable.concat(
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(500, TimeUnit.MILLISECONDS).take(3),
                Observable.interval(100, TimeUnit.MILLISECONDS).take(3))
                .scan(0, (acc, v) -> acc + 1)
                .timeout(200, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println,
                        System.out::println);
    }

}
