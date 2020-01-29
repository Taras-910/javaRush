package com.javarush.task.task39.task3907.workers;

public class NormalWorker implements Worker, Eater, Sleeper {
    @Override
    public void work() {
        System.out.println("NormalWorker is working!");
    }

    @Override
    public void eat() {
        System.out.println("NormalWorker is eating!");
    }

    @Override
    public void sleep() {
        System.out.println("NormalWorker is sleeping!");
    }
}

/*
ISP
Из-за того что интерфейс Worker содержит слишком много разноплановых методов, классам которые хотят поддерживать только часть функциональности, необходимо реализовывать и те методы, которые им на самом деле не нужны.
Было бы намного удобнее иметь несколько интерфейсов, каждый из которых описывал бы отдельную функциональность.

Создай интерфейсы Sleeper и Eater в пакете workers, подумай как провести корректный рефакторинг и внеси необходимые изменения.

Требования:
1. В интерфейсе Sleeper должен быть объявлен только метод sleep.
2. В интерфейсе Eater должен быть объявлен только метод eat.
3. В интерфейсе Worker должен остаться только метод work.
4. Класс NormalWorker должен поддерживать интерфейсы Worker, Sleeper, Eater.
5. Класс LazyPerson должен поддерживать только интерфейсы Sleeper, Eater.
6. Класс RobotWorker должен поддерживать только интерфейс Worker.
 */