package com.samples.academy.into_to_rxjava;

import rx.Subscriber;

/**
 * Created by abdellahselassi on 4/13/17.
 */

public class PrintSubscriber extends Subscriber {
    private String message;

    public PrintSubscriber(String repeatWhen) {
        this.message = repeatWhen;
    }

    @Override
    public void onCompleted() {
        System.out.println("Completed");
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Object o) {
        System.out.println(message + " " + o);
    }
}
