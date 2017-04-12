package com.samples.academy.into_to_rxjava;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HotAndColdObservables {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void cold() throws InterruptedException {
        Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS).map(i -> i * 2);

        cold.subscribe(i -> System.out.println("First: " + i));
        Thread.sleep(500);
        cold.subscribe(i -> System.out.println("Second: " + i));
    }

    @Test
    public void connect() throws InterruptedException {
        ConnectableObservable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS).publish();

        cold.connect();

        cold.subscribe(i -> System.out.println("First: " + i));
        cold.subscribe(i -> System.out.println("Second: " + i));
        Thread.sleep(1500);
    }

    @Test
    public void disconnect() throws InterruptedException {
        ConnectableObservable<Long> connectable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        Subscription s = connectable.connect();

        connectable.subscribe(i -> System.out.println(i));

        Thread.sleep(1000);
        System.out.println("Closing connection");
        s.unsubscribe();

        System.out.println("Reconnecting");
        s = connectable.connect();
    }

    @Test
    public void refCount() throws InterruptedException {
        Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS).publish().refCount();

        Subscription s1 = cold.subscribe(i -> System.out.println("First: " + i));
        Thread.sleep(500);

        Subscription s2 = cold.subscribe(i -> System.out.println("Second: " + i));
        Thread.sleep(500);

        System.out.println("Unsubscribe second");
        s2.unsubscribe();

        Thread.sleep(500);
        System.out.println("Unsubscribe first");
        s1.unsubscribe();

        System.out.println("First connection again");
        Thread.sleep(500);
        s1 = cold.subscribe(i -> System.out.println("First: " + i));
        Thread.sleep(500);
    }

}