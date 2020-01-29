package com.javarush.task.task36.task3609;

public class CarController {
    private CarModel model;
    private SpeedometerView view;

    public CarController(CarModel model, SpeedometerView view) {
        this.model = model;
        this.view = view;
    }

    public String getCarBrand() {
        return model.getBrand();
    }

    public String getCarModel() {
        return model.getModel();
    }

    public void setCarSpeed(int speed) {
        model.setSpeed(speed);
    }

    public int getCarSpeed() {
        return model.getSpeed();
    }

    public int getCarMaxSpeed() {
        return model.getMaxSpeed();
    }

    public void updateView() {
        view.printCarDetails(getCarBrand(), getCarModel(), getCarSpeed());
    }

/*
    public void increaseSpeed(int seconds) {
        if (speed < maxSpeed) {
            speed += (3.5 * seconds);
        }
        if (speed > maxSpeed) {
            speed = maxSpeed;
        }
    }

    public void decreaseSpeed(int seconds) {
        if (speed > 0) {
            speed -= (12 * seconds);
        }
        if (speed < 0) {
            speed = 0;
        }
    }
*/

    public void increaseSpeed(int seconds) {
        if (getCarSpeed() < getCarMaxSpeed()) {
            setCarSpeed(getCarSpeed()+(int)(3.5 * seconds));
        }
        if (getCarSpeed() > getCarMaxSpeed()) {
            setCarSpeed(getCarMaxSpeed());
        }
    }

    public void decreaseSpeed(int seconds) {
        if (getCarSpeed() > 0) {
            setCarSpeed(getCarSpeed()-(12 * seconds));
        }
        if (getCarSpeed() < 0) {
            setCarSpeed(0);
        }
    }




}

/*Рефакторинг MVC
Перемести некоторые методы в нужные классы, что бы получить паттерн MVC.
Если необходимо - внеси изменения в метод main, которые отражают внесенные тобой изменения.
Поведение программы при этом не должно измениться.
НЕ изменяй названия классов, методов и полей.


Требования:
1. Вывод программы должен остаться без изменений.
2. Необходимо переместить метод void increaseSpeed(int) из класса CarModel в класс CarController.
3. Необходимо переместить метод void decreaseSpeed(int) из класса CarModel в класс CarController.
4. В методе main класса Solution метод increaseSpeed необходимо вызывать у контроллера, а не у модели.
5. В методе main класса Solution метод decreaseSpeed необходимо вызывать у контроллера, а не у модели.
*/
