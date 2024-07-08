package com.example.task2;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        setSize(WIDTH, HEIGHT);
        setTitle("Bounce program");

        JLabel counterLabel = new JLabel("Score: 0");
        canvas = new BallCanvas(counterLabel);
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = getContentPane();

        content.add(canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(_ -> {
            Ball b = new Ball(canvas);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.start();
            System.out.println("Thread name = " + thread.getName());
        });

        buttonStop.addActionListener(_ -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(counterLabel);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
