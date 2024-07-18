package com.example.task2;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pocket {
    public static final int RADIUS = 10;
    private final int x;
    private final int y;

    public Pocket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        var pocketDiameter = 2 * RADIUS;
        g2.fill(new Ellipse2D.Double(x, y, pocketDiameter, pocketDiameter));
    }

    public boolean containsBall(Ball ball) {
        var distance = Math.sqrt(Math.pow(ball.getX() - x, 2) + Math.pow(ball.getY() - y, 2));
        var diameter = RADIUS * 2;
        return distance <= diameter;
    }
}