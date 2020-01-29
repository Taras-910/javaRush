package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.Controller;
import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void setEventListener(EventListener eventListener){
        field.setEventListener(eventListener);
    }

    public void init() {
        field = new Field(this);
        add(field);
        updateLevelAndSize();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(){ field.repaint(); }

    public GameObjects getGameObjects(){  // должен запросить игровые объекты у controller <- y model
        GameObjects game = controller.getGameObjects();
        updateLevelAndSize();
        return game;
    }

    public int[] getLevelAndSize(){
        return controller.getLevelAndSize();
    }

    public void completed(int level){          // будет сообщать пользователю, что уровень level пройден
        update();
        JOptionPane.showMessageDialog(null, "level "+level+" passed");
        controller.startNextLevel();
    }

    private void updateLevelAndSize() {
        int[] levelAndSize = getLevelAndSize();
        setSize(levelAndSize[1], levelAndSize[2]);  // устанавливаем размеры поля в зависимости от уровня
        setTitle("Сокобан  **" + levelAndSize[0] + "** Level");
    }
}

