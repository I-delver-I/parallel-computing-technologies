package com.example.task4;

public class BallThread extends Thread {
    private final Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        final var iterationsCount = 1000;
        final var sleepTime = 5;

        try {
            for (var i = 1; i < iterationsCount; i++) {
                while (b.isStopped()) {
                    Thread.sleep(0);
                }

                b.move();
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
