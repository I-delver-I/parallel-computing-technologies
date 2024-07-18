package com.example.task5;

public class SynchronizedConsolePrintThread extends Thread {
    private final char character;
    private static final Object lock = new Object();
    private static int turn = 0; // Variable to manage turn-taking
    private final int myTurn;

    public SynchronizedConsolePrintThread(char character, int myTurn) {
        this.character = character;
        this.myTurn = myTurn;
    }

    public void run() {
        int lineCountToPrint = 100;
        int characterCountToPrint = 20;

        for (int i = 0; i < lineCountToPrint; i++) {
            for (int j = 0; j < characterCountToPrint; j++) {
                synchronized (lock) {
                    while (turn != myTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    System.out.print(this.character);

                    turn = (turn + 1) % 2;
                    lock.notifyAll();
                }
            }

            synchronized (lock) {
                if (turn != myTurn) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println();
                turn = (turn + 1) % 2;
                lock.notifyAll();
            }
        }
    }
}
