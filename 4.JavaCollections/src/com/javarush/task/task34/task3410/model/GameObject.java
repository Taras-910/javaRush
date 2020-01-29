package com.javarush.task.task34.task3410.model;
import java.awt.*;
import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public abstract class GameObject {
    // Это будет позиция и размер объекта для отрисовки
    private int x;
    private int y;
    public int width;
    public int height;

    public GameObject(int x, int y) {// Размер, будет храниться внутри объекта, используется только при его отрисовке
        this.x = x;
        this.y = y;
        this.width = FIELD_CELL_SIZE;  //размер ячейки игрового поля
        this.height = FIELD_CELL_SIZE; // этот размер будет участвовать в расчёте движения и столкновений объектов
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Graphics graphics);

    public int getX() { return x;}

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
