package com.javarush.task.task34.task3410.controller;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.Direction;
import com.javarush.task.task34.task3410.model.GameObjects;
import com.javarush.task.task34.task3410.model.Model;
import com.javarush.task.task34.task3410.view.View;
import com.javarush.task.task34.task3410.model.Direction;


public class Controller implements EventListener {

    private View view;
    private Model model;

    public Controller() {
        model = new Model();
        model.restart();
        model.setEventListener(this);
        view = new View(this);
        view.init();
        view.setEventListener(this);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {             //должен перезапускать модель и обновлять представление
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {       //должен запускать у модели новый уровень и обновлять представление
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }

    public GameObjects getGameObjects(){  // должен запросить игровые объекты у модели и вернуть их
        return model.getGameObjects();
    }

    public int[] getLevelAndSize() {
        return model.getLevelAndSize();
    }
}
