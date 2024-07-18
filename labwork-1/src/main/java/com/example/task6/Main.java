package com.example.task6;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int operationsCount = 100_000;

    public static void main(String[] args) {
        var counter = new Counter();
        syncMethod(counter);
        syncBlock(counter);
        blockObj(counter);
    }

    private static void blockObj(Counter counter) {
        var lock = new ReentrantLock();

        var thread1 = new Thread(() -> {
            for (var i = 0; i < operationsCount; i++) {
                lock.lock();
                counter.increment();
                lock.unlock();
            }
        });

        var thread2 = new Thread(() -> {
            for (var i = 0; i < operationsCount; i++) {
                lock.lock();
                counter.decrement();
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Blocked object counter value: " + counter.getCount());
    }

    private static void syncBlock(Counter counter) {
        var thread1 = new Thread(() -> {
            for (var i = 0; i < operationsCount; i++) {
                counter.syncBlockIncrement();
            }
        });

        var thread2 = new Thread(() -> {
            for (var i = 0; i < operationsCount; i++) {
                counter.syncBlockDecrement();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Synced block counter value: " + counter.getCount());
    }

    private static void syncMethod(Counter counter) {
        var thread1 = new Thread(() -> {
            for (var i = 0; i < operationsCount; i++) {
                counter.syncIncrement();
            }
        });

        var thread2 = new Thread(() -> {
            for (var i = 0; i < operationsCount; i++) {
                counter.syncDecrement();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Synced method counter value: " + counter.getCount());
    }
}
