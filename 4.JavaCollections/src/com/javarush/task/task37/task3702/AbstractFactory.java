package com.javarush.task.task37.task3702;

public interface AbstractFactory {
    public <T extends Human> T getPerson(int age);
}
