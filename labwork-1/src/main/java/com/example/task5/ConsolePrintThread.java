package com.example.task5;

public class ConsolePrintThread extends Thread {
    private final char character;

    public ConsolePrintThread(char character) {
        this.character = character;
    }

    public void run() {
        var lineCountToPrint = 100;
        var characterCountToPrint = 20;

        for (var i = 0; i < lineCountToPrint; i++) {
            for (var j = 0; j < characterCountToPrint; j++) {
                System.out.print(this.character);
            }

            System.out.println();
        }
    }
}