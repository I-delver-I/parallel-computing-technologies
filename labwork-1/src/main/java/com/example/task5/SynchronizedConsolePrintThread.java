package com.example.task5;

public class SynchronizedConsolePrintThread extends Thread {
    private final char character;
    private static final Object lock = new Object();
    private static int currentTurn = 0;
    private final int myTurn;
    private static int totalThreads = 0;

    public SynchronizedConsolePrintThread(char character) {
        this.character = character;
        synchronized (lock) {
            this.myTurn = totalThreads++;
        }
    }

    @Override
    public void run() {
        var lineCountToPrint = 100;
        var characterCountToPrint = 20;

        for (var i = 0; i < lineCountToPrint; i++) {
            for (var j = 0; j < characterCountToPrint; j++) {
                synchronized (lock) {
                    if (currentTurn != myTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.print(this.character);
                    currentTurn = (currentTurn + 1) % totalThreads;
                    lock.notifyAll();
                }
            }
        }
    }
}
