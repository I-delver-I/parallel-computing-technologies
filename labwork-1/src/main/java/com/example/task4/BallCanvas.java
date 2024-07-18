package com.example.task4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();

    public void  startBalls() {
        for (var ball : balls) {
            ball.startMovement();
        }
    }

    public void stopBalls() {
        for (var ball : balls) {
            ball.stop();
        }
    }

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
