package com.samples.academy.into_to_rxjava.sequence_basics;

import com.samples.academy.into_to_rxjava.PrintSubscriber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by abdellahselassi on 4/14/17.
 */

public class Aggregation {

    @Test
    public void count() {
        Observable<Integer> values = Observable.range(0, 3);

        values
                .subscribe(new PrintSubscriber("Values"));
        values
                .count()
                .subscribe(new PrintSubscriber("Count"));
    }

    @Test
    public void name() throws Exception {

    }

    @Test
    public void first() throws InterruptedException {
        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        values
                .first(v -> v > 5)
                .subscribe(new PrintSubscriber("First"));

        Thread.sleep(1000);
    }

    @Test
    public void last() throws InterruptedException {
        Observable<Integer> values = Observable.range(0, 10);

        values
                .last(v -> v > 5)
                .subscribe(new PrintSubscriber("Last"));

        Thread.sleep(2000);
    }

    @Test
    public void single() throws InterruptedException {
        Observable<Integer> values = Observable.range(0, 10);

        values
                .take(10)
                .single(v -> v == 5) // Emits a result
                .subscribe(new PrintSubscriber("Single1"));

        values
                .single(v -> v == 6) // Never emits
                .subscribe(new PrintSubscriber("Single2"));


    }

    /**
     * Custom aggregators
     */


    @Test
    public void reduce() {
        Observable<Integer> values = Observable.range(0, 5);

        values
                .reduce((i1, i2) -> i1 + i2)
                .subscribe(new PrintSubscriber("Sum"));

        values
                .reduce((i1, i2) -> (i1 > i2) ? i2 : i1)
                .subscribe(new PrintSubscriber("Min"));


        Observable<String> words = Observable.just("Rx", "is", "easy");

        words
                .reduce(0, (acc, next) -> acc + 1)
                .subscribe(new PrintSubscriber("Count"));

    }

    @Test
    public void scan() {
        Observable<Integer> values = Observable.range(0, 5);

        values
                .scan((i1, i2) -> i1 + i2)
                .subscribe(new PrintSubscriber("Sum"));
    }

    /**
     * Aggregation to collections
     */

    @Test
    public void reduceToList() {
        Observable<Integer> values = Observable.range(10, 5);

        values
                .reduce(
                        new ArrayList<Integer>()
                        ,
                        (acc, value) -> {
                            acc.add(value);
                            return acc;
                        })
                .subscribe(v -> System.out.println(v));
    }

    @Test
    public void collect() {
        Observable<Integer> values = Observable.range(10, 5);

        values
                .collect(
                        () -> new ArrayList<Integer>(),
                        (acc, value) -> acc.add(value))
                .subscribe(v -> System.out.println(v));
    }

    @Test
    public void toList() {
        Observable<Integer> values = Observable.range(10, 5);

        values
                .toList()
                .subscribe(v -> System.out.println(v));
    }

    @Test
    public void toSortedList() {
        Observable<Integer> values = Observable.range(10, 5);

        values
                .toSortedList((i1, i2) -> i2 - i1)
                .subscribe(v -> System.out.println(v));
    }


    @Test
    public void toMap() {
        Observable<Person> values = Observable.just(
                new Person("Will", 25),
                new Person("Nick", 40),
                new Person("Saul", 35)
        );

        values
                .toMap(
                        person -> person.name,
                        person -> person.age)
                .subscribe(new PrintSubscriber("toMap"));
    }


    @Test
    public void toMultimap() {
        Observable<Person> values = Observable.just(
                new Person("Will", 35),
                new Person("Nick", 40),
                new Person("Saul", 35)
        );

        values
                .toMultimap(
                        person -> person.age,
                        person -> person.name)
                .subscribe(new PrintSubscriber("toMap"));
    }

    @Test
    public void groupBy() {
        Observable<String> values = Observable.just(
                "first",
                "second",
                "third",
                "forth",
                "fifth",
                "sixth"
        );

        values.groupBy(word -> word.charAt(0))
                .subscribe(
                        group -> group.last()
                                .subscribe(v -> System.out.println(group.getKey() + ": " + v))
                );
    }

    /**
     * Nested observables
     */
    @Test
    public void nest() {
        Observable.range(0, 3)
                .nest()
                .subscribe(ob -> ob.subscribe(System.out::println));
    }


    class Person {
        public final String name;
        public final Integer age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }


}
