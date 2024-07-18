package com.example.task5;

public class ConsolePrintThread extends Thread {
    private final char character;

    public ConsolePrintThread(char character) {
        this.character = character;
    }

    public void run() {
        int lineCountToPrint = 100;
        int characterCountToPrint = 20;

        for (int i = 0; i < lineCountToPrint; i++) {
            for (int j = 0; j < characterCountToPrint; j++) {
                System.out.print(this.character);
            }

            System.out.println();
        }
    }
}