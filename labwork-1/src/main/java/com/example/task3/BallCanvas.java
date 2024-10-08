package com.example.task3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();

    public void add(Ball b) {
        balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D)g;

        for (var b : balls) {
            b.draw(g2);
        }
    }
}
