package com.javarush.task.task39.task3906;

public class ElectricPowerSwitch {
    Switchable securitySystem;

    public ElectricPowerSwitch(Switchable object) {
        this.securitySystem = object;
    }

    public void press() {
        System.out.println("Pressed the power switch.");

        if (securitySystem.isOn()) {
            securitySystem.turnOff();
        } else {
            securitySystem.turnOn();
        }
    }
}

/*
Интерфейсы нас спасут!
Амиго, помоги... Совсем не знаю как быть. Четко следовал ТЗ, в котором требовалось создать систему включения сигнализации.
Создал сигнализацию (SecuritySystem), кнопку включения (ElectricPowerSwitch)
и проверил работоспособность в методе main класса Solution.
Но вдруг пришло новое требование.
Оказывается им так понравилось включать сигнализацию одной кнопкой,
которая сама определяет действие которое необходимо осуществить,
что теперь хотят к ней прикрутить еще и возможность включать свет.
Я создал класс LightBulb по образу и подобию класса SecuritySystem,
но не могу придумать как его связать с кнопкой, ведь она может работать только с сигнализацией...

Может попробовать создать для них общий интерфейс?

Требования:
1. Классы LightBulb и SecuritySystem должны иметь общий интерфейс с именем Switchable.
2. В классе ElectricPowerSwitch не должно быть полей типа SecuritySystem или LightBulb.
3. В классе ElectricPowerSwitch должно присутствовать только одно поле.
4. Логика класса ElectricPowerSwitch и метода press должна остаться неизменной.
 */