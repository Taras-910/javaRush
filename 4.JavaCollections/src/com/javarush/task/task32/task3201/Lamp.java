package com.javarush.task.task32.task3201;

public class Lamp implements ElectricityCosumer{

    public void lightOn() {
        System.out.println("Лампа зажглась");
    }

    @Override
    public void electricityOn(Object sender) {
        lightOn();
    }
}
