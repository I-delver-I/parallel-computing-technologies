package com.example.task1;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Ball {
    private final Component canvas;
    private static final int X_SIZE = 20;
    private static final int Y_SIZE = 20;
    private int x;
    private int y;
    private int xMoveStep = 2;
    private int yMoveStep = 2;

    public Ball(Component c) {
        canvas = c;

        if (Math.random() < 0.5)
        {
            x = new Random().nextInt(canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(canvas.getHeight());
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.DARK_GRAY);
        g2.fill(new Ellipse2D.Double(x, y, X_SIZE, Y_SIZE));
    }

    public void move() {
        x += xMoveStep;
        y += yMoveStep;

        if (x < 0) {
            x = 0;
            xMoveStep = -xMoveStep;
        }

        if (x + X_SIZE > canvas.getWidth()) {
            x = canvas.getWidth() - X_SIZE;
            xMoveStep = -xMoveStep;
        }

        if (y < 0) {
            y = 0;
            yMoveStep = -yMoveStep;
        }

        if (y + Y_SIZE > canvas.getHeight()) {
            y = canvas.getHeight() - Y_SIZE;
            yMoveStep = -yMoveStep;
        }

        canvas.repaint();
    }
}
