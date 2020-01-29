package com.javarush.task.task32.task3201;

public class Radio implements ElectricityCosumer{

    public  void playMusic(){
        System.out.println("Radio plays");
    }

    @Override
    public void electricityOn(Object sender) {
        playMusic();
    }
}
