package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.view.View;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Model {
    private View view;
    private Provider [] providers;

    public Model(View view, Provider... providers) throws IllegalArgumentException {

        if(view == null || providers == null || providers.length <1) throw new IllegalArgumentException();
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city){
        HashSet<Vacancy> set = new HashSet<>();
        for(Provider provider : providers){
            try {
//System.out.println("Model 25 ");
                set.addAll(provider.getJavaVacancies(city));
            } catch (IOException e) { }
        }
//System.out.println("Model 29 ");
        List<Vacancy> list = new ArrayList<>(set);
        view.update(list);
    }
}
/*
Aggregator (11)
В Model есть метод selectCity, в него передается название города, для которого выбираются вакансии.
Очевидно, что этот метод будет вызываться контроллером, т.к. он принимает решение, какую модель использовать.

1. Добавь в Controller новое поле Model model.

2. Удали метод scan() из Controller, его логика переместилась в модель.

3. Удали конструктор, toString и поле providers из контроллера.

4. Создай конструктор в Controller с аргументом Model.
На некорректные данные брось IllegalArgumentException

5. В Controller создай публичный метод void onCitySelect(String cityName), в котором вызови нужный метод модели.

6. Удали код из метода main. Этот код уже не валидный.


Требования:
1. В классе Controller добавь новое поле Model model.
2. Удали из Controller метод scan, метод toString, конструктор и поле providers.
3. Добавь в Controller новый конструктор с аргументом Model. На некорректные данные брось IllegalArgumentException.
4. Создай в Controller публичный метод void onCitySelect(String cityName), в котором вызови нужный метод модели.
5. Удали старый код из метода main.


Aggregator (10)
У View есть метод update, в него передается список вакансий для отображения.
Очевидно, что этот метод будет вызываться моделью, т.к. только она получает данные.
Пришло время создать модель.

1. Создай класс Model в пакете model.

2. Добавь два поля - 1) вью, 2) массив провайдеров.

3. Создай конструктор с двумя параметрами - 1) вью, 2) массив провайдеров.
На неправильные данные кинь IllegalArgumentException.

4. Создай публичный метод void selectCity(String city).

5. Реализуй логику метода selectCity:
5.1. получить вакансии с каждого провайдера,
5.2. обновить вью списком вакансий из п.5.1.


Требования:
1. Создай класс Model в пакете model.
2. В классе Model добавь два поля: вью и массив провайдеров.
3. Создай конструктор с двумя параметрами: вью и массив провайдеров. На неправильные данные кинь IllegalArgumentException.
4. В класс Model добавь метод public void selectCity(String city).
5. Реализуй логику метода selectCity. Он должен получать вакансии с каждого провайдера и передавать в метод update у вью.
 */