package com.example.task4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();

    public void  startBalls() {
        for (Ball ball : balls) {
            ball.startMovement();
        }
    }

    public void stopBalls() {
        for (Ball ball : balls) {
            ball.stop();
        }
    }

    public void add(Ball b) {
        balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (Ball b : balls) {
            b.draw(g2);
        }
    }
}
