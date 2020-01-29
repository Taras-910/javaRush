package com.javarush.task.task34.task3410.model;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public abstract class CollisionObject extends GameObject {

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction){
        int x = this.getX();
        int y = this.getY();

        switch (direction) {
            case LEFT:  x = getX() - FIELD_CELL_SIZE;
                break;
            case RIGHT: x = getX() + FIELD_CELL_SIZE;
                break;
            case UP:    y = getY() - FIELD_CELL_SIZE;
                break;
            case DOWN:  y = getY() + FIELD_CELL_SIZE;
        }
        return x == gameObject.getX() && y == gameObject.getY(); //Столкновением считать совпадение координат x и y
    }
}
