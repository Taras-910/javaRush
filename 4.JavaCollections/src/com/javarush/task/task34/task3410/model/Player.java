package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void draw(Graphics graphics) {

        graphics.setColor(new Color(255, 249, 9));
        graphics.fillOval(getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight());
    }
}
