package com.example.task6;

public class Counter {
    private int count = 0;
    private final Object lock = new Object();

    public void syncBlockDecrement() {
        synchronized (lock) {
            count--;
        }
    }

    public void syncBlockIncrement() {
        synchronized (lock) {
            count++;
        }
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public synchronized void syncIncrement() {
        count++;
    }

    public synchronized void syncDecrement() {
        count--;
    }

    public synchronized int getCount() {
        return count;
    }
}
