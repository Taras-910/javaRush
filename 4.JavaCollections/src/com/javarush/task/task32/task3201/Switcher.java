package com.javarush.task.task32.task3201;

import java.util.ArrayList;
import java.util.List;

public class Switcher {

    private List< ElectricityCosumer> listeners = new ArrayList<>();

    // подписка - отвечает за массив подписчиков на событие
    public void addElectricityListener(ElectricityCosumer listener){
        listeners.add(listener);
    }

    //отписка от собитыя
    public void removeElectricityListener(ElectricityCosumer listener){
        listeners.remove(listener);
    }

    public void switchOn(){
        System.out.println("Выключатель включен");
        for(ElectricityCosumer c: listeners){
            if(c != null) c.electricityOn(this);
        }
    }
}
