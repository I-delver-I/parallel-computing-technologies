package com.example.task3;

import java.awt.*;

public class BallThread extends Thread {
    private final Ball b;

    public BallThread(Ball ball) {
        b = ball;

        if (b.getColor() == Color.RED) {
            setPriority(Thread.MAX_PRIORITY);
        }
        else {
            setPriority(Thread.MIN_PRIORITY);
        }
    }

    @Override
    public void run() {
        final int iterationsCount = 10000;
        final int sleepTime = 5;

        try {
            for (int i = 1; i < iterationsCount; i++) {
                b.move();
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
