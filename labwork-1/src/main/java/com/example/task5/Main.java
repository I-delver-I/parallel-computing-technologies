package com.example.task5;

public class Main {
    public static void main(String[] args) {
//        var thread1 = new ConsolePrintThread('|');
//        var thread2 = new ConsolePrintThread('-');
        var thread3 = new SynchronizedConsolePrintThread('|', 0);
        var thread4 = new SynchronizedConsolePrintThread('-', 1);

//        thread1.start();
//        thread2.start();
        thread3.start();
        thread4.start();
    }
}
