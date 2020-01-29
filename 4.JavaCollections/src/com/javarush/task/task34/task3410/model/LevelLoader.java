package com.javarush.task.task34.task3410.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public class LevelLoader {
    private Path levels; // путь

    public LevelLoader(Path levels) {
        this.levels = levels;
    }
    public int xField;
    public int yField;

    public int getXField() { return xField; }
    public void setXField(int xField) { this.xField = xField; }
    public int getYField() { return yField; }
    public void setYField(int yField) { this.yField = yField; }

    public GameObjects getLevel(int level){
        if(level > 60) level -= 60;
        GameObjects gameObjects = null;
        Set<Box> boxSet = new HashSet<>();
        Set<Home> homeSet = new HashSet<>();
        Set<Wall> wallSet = new HashSet<>();
        Player player = null;


        List<String> rows = null;           // весь массив

        try {
            rows = Files.readAllLines(levels, StandardCharsets.UTF_8);
        } catch (IOException e) { System.out.println("fault IO"); }

        int lev = 0;
        boolean flag = false;
        int y = FIELD_CELL_SIZE/2;

        for(String s : rows) {
            if (s.contains("Maze:")){
                lev = Integer.parseInt(s.split(": ")[1]);
                if (lev == level){
                    flag = true;
                }
            }

            if (s.isEmpty() || !flag) continue;
            else {
                if (s.contains("Size X:")){
                    setXField(Integer.parseInt(s.split(": ")[1])*FIELD_CELL_SIZE);
                    continue;
                }
                if (s.contains("Size Y:")) {
                    setYField(Integer.parseInt(s.split(": ")[1])*FIELD_CELL_SIZE + FIELD_CELL_SIZE);
                    continue;
                }
                if (!s.contains(":") && !s.equals("*************************************")) {
                    int x = FIELD_CELL_SIZE / 2;
                    for (int a = 0; a < s.length(); a++) {   // проход вдоль ряда
                        switch (s.charAt(a)) {
                            case 'X':
                                wallSet.add(new Wall(x, y));
                                break;
                            case '.':
                                homeSet.add(new Home(x, y));
                                break;
                            case '*':
                                boxSet.add(new Box(x, y));
                                break;
                            case '@':
                                player = new Player(x, y);
                                break;
                            case '&':
                                homeSet.add(new Home(x, y));
                                boxSet.add(new Box(x, y));
                                break;
                        }
                        x += FIELD_CELL_SIZE;
                    }
                    y += FIELD_CELL_SIZE;
                }
                if (s.equals("*************************************")) {
                    gameObjects = new GameObjects(wallSet, boxSet, homeSet, player);
                    flag = false;
                    y = FIELD_CELL_SIZE/2;
                }
            }
        }
        return gameObjects;
    }

}
