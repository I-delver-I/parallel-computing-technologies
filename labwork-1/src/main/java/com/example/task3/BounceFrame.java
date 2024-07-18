package com.example.task3;

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

        JButton buttonStartBlue = new JButton("Start blue ball");
        JButton buttonStartRed = new JButton("Start red ball");
        JButton buttonStop = new JButton("Stop");

        buttonStartBlue.addActionListener(_ -> {
            int blueBallsCountToCreate = 1000;

            for (int i = 0; i < blueBallsCountToCreate; i++) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }
        });

        buttonStartRed.addActionListener(_ -> {
            Ball b = new Ball(canvas, Color.RED);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.start();
            System.out.println("Thread name = " + thread.getName());
        });

        buttonStop.addActionListener(_ -> System.exit(0));

        buttonPanel.add(buttonStartBlue);
        buttonPanel.add(buttonStartRed);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
