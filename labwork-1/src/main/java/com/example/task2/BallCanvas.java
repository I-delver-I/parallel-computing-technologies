package com.example.task2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Pocket> pockets = new ArrayList<>();
    private int score = 0;
    private final JLabel scoreDisplay;

    public BallCanvas(JLabel counterLabel) {
        scoreDisplay = counterLabel;
        add(scoreDisplay);
    }

    public boolean isBallInPocket(Ball b) {
        for (Pocket pocket : pockets) {
            if (pocket.containsBall(b)) {
                return true;
            }
        }

        return false;
    }

    private void initPockets() {
        var pocketDiameter = Pocket.RADIUS * 2;

        pockets.add(new Pocket(0, 0)); // Top-left corner
        pockets.add(new Pocket(getWidth() - pocketDiameter, 0)); // Top-right corner
        pockets.add(new Pocket(0, getHeight() - pocketDiameter)); // Bottom-left corner
        pockets.add(new Pocket(getWidth() - pocketDiameter, getHeight() - pocketDiameter)); // Bottom-right corner
    }

    public synchronized void incrementScore() {
        score++;
        scoreDisplay.setText("Score: " + score);
    }

    public void add(Ball b) {
        balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D)g;
        initPockets();

        for (var pocket : pockets) {
            pocket.draw(g2);
        }

        for (var b : balls) {
            if (b.isInPocket()) {
                incrementScore();
            }
            else {
                b.draw(g2);
            }
        }

        balls.removeIf(Ball::isInPocket);
    }
}
