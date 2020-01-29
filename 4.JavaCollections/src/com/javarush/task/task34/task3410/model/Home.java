package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x, y);
        this.width = 2;
        this.height = 2;
    }

    public void draw(Graphics graphics){

        graphics.setColor(new Color(255, 5, 0));
        graphics.drawOval(getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight());
    }
}
