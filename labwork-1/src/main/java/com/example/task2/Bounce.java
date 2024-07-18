package com.example.task2;

import javax.swing.*;

public class Bounce {
    public static void main(String[] args) {
        var frame = new BounceFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        System.out.println("Thread name = " + Thread.currentThread().getName());
    }
}
