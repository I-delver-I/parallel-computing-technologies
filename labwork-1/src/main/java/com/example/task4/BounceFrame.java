package com.example.task4;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        setSize(WIDTH, HEIGHT);
        setTitle("Bounce program");

        canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());
        Container content = getContentPane();

        content.add(canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton buttonStart = new JButton("Add blue ball");
        JButton buttonJoin  = new JButton("Join red ball");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(_ -> {
            Ball b = new Ball(canvas, Color.BLUE);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.start();
            System.out.println("Thread name = " + thread.getName());
        });

        buttonJoin.addActionListener(_ -> {
            canvas.stopBalls();
            Ball b = new Ball(canvas, Color.RED);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.start();

            Thread threadToJoin = new Thread(() -> {
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                canvas.startBalls();
            });

            threadToJoin.start();
        });

        buttonStop.addActionListener(_ -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
