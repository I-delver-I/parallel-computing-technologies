package com.example.task5;

public class Main {
    public static void main(String[] args) {
//        ConsolePrintThread thread1 = new ConsolePrintThread('|');
//        ConsolePrintThread thread2 = new ConsolePrintThread('-');
        SynchronizedConsolePrintThread thread3 = new SynchronizedConsolePrintThread('|', 0);
        SynchronizedConsolePrintThread thread4 = new SynchronizedConsolePrintThread('-', 1);

//        thread1.start();
//        thread2.start();
        thread3.start();
        thread4.start();
    }
}
