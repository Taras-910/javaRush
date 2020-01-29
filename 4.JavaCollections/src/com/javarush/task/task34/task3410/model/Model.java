package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;

public class Model {

    public static int FIELD_CELL_SIZE = 30;
    public EventListener eventListener;
    public GameObjects gameObjects;  // будет хранить наши игровые объекты
    public int currentLevel = 1;     // текущий уровень

    public LevelLoader levelLoader = new LevelLoader(Paths.get("4.JavaCollections/src/com/javarush/task/task34/task3410/res/levels.txt"));


    public void setEventListener(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects(){
        return gameObjects;
    }

    public int[] getLevelAndSize(){
        return new int[]{currentLevel, levelLoader.getXField(), levelLoader.getYField()};
    }

    public void restartLevel(int level){
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart(){
        restartLevel(currentLevel);
    }

    public void startNextLevel(){
        currentLevel++;
        restartLevel(currentLevel);
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction){
        boolean result = false;
        for(Wall w : gameObjects.getWalls()) {
                if(gameObject.isCollision(w, direction)) result = true;
        }
        return result;
    }

     public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction){

        Player player = gameObjects.getPlayer();
        GameObject object = null;

        for (GameObject gameObject : gameObjects.getAll()) {
            if (!(gameObject instanceof Player)                      // объект не Player
                    && !(gameObject instanceof Home)                 // объект не Home
                    && player.isCollision(gameObject, direction)) {  // если есть столкновение запоминаем объект
                object = gameObject;
            }
        }

        if (object == null) return false;

        if (object instanceof Box) {                                   // если объект - это Box
            Box stopedBox = (Box) object;                              // запоминаем его в stopedBox
            if (checkWallCollision(stopedBox, direction)) { //если есть столкновение stopedBox со стеной возвращаем true
                return true;
            }
            for (Box box : gameObjects.getBoxes()) {         // просматриваем все Box,
                if (stopedBox.isCollision(box, direction)) { // если есть столкновение с другим Box возвращаем true
                    return true;
                }
            }

            switch (direction) {
                case LEFT:
                    stopedBox.move(-FIELD_CELL_SIZE, 0);
                    break;
                case RIGHT:
                    stopedBox.move(FIELD_CELL_SIZE, 0);
                    break;
                case UP:
                    stopedBox.move(0, -FIELD_CELL_SIZE);
                    break;
                case DOWN:
                    stopedBox.move(0, FIELD_CELL_SIZE);
            }
        }
        return false;
    }

    public void checkCompletion(){
        boolean result = true;
        for(Home h : gameObjects.getHomes()){
            boolean tempResult = false;

            for(Box b : gameObjects.getBoxes()){
                if(h.getX() == b.getX() && h.getY() == b.getY()) tempResult = true;
            }
            if(!tempResult){
                result = false;
            }
        }
        if(result) eventListener.levelCompleted(currentLevel);
    }

    public void move(Direction direction){
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player, direction)) return;
        if (checkBoxCollisionAndMoveIfAvaliable(direction)) return;
        switch (direction){
            case LEFT:
                player.move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                player.move(FIELD_CELL_SIZE, 0);
                break;
            case UP:
                player.move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                player.move(0, FIELD_CELL_SIZE);
                break;
        }
        checkCompletion();
    }

//    public int getXField() {
//        return levelLoader.getXField();
//    }
//
//    public int getYField() {
//        return levelLoader.getYField();
//    }
//
//    public int getLevel() {
//        return currentLevel;
//    }
}
