package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Provider {
    Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString) throws IOException {
//System.out.println("Provider 20 this.strategy = "+this.strategy);

        if (this.strategy == null) {
//System.out.println("Provider 24 ");
            return new ArrayList<>();
        }
        else {
//System.out.println("Provider 28 strategy ");
//System.out.println("Provider 29 " +strategy.getVacancies(searchString));
            return strategy.getVacancies(searchString);
        }
    }
}


/*
Aggregator (5)
1. Добавь в интерфейс метод getVacancies(String searchString), который будет возвращать список вакансий.

2. Поправь ошибки в классе HHStrategy.

3. Вернись в метод getJavaVacancies класса Provider, реализуй его логику из расчета, что всех данных хватает.

4. Давай попробуем запустить и посмотреть, как работает наша программа.
В методе main вместо вывода на экран напиши controller.scan();
Воспользуйся подсказкой IDEA и создай метод.
Внутри метода пройдись по всем провайдерам и собери с них все вакансии, добавь их в список. Выведи количество вакансий в консоль.

5. Исправь NPE, если появляется это исключение (поставь заглушку).

Требования:
1. В интерфейсе Strategy добавь метод getVacancies(String searchString).
2. Обнови класс HHStrategy, что бы в нем не было ошибок.
3. В классе Provider реализуй логику метода getJavaVacancies.
4. В методе main вместо вывода на экран добавь вызов controller.scan(). Реализуй этот метод согласно заданию.
5. Вызов main не должен кидать NullPointerException. Поставь заглушки в необходимых местах.



Aggregator (3)
Начиная с этого задании ты начнешь писать логику получения данных с сайта.
Эта логика будет полностью сосредоточена в классах, реализующих Strategy.

Провайдер в данном случае выступает в качестве контекста, если мы говорим о паттерне Стратегия.
В провайдере должен быть метод, который будет вызывать метод стратегии для выполнения главной операции.
Этот метод будет возвращать все java вакансии с выбранного сайта (ресурса).

1. В корне задачи создай пакет vo (value object), в котором создай класс Vacancy.
Этот класс будет хранить данные о вакансии.

2. В Provider создай метод List<Vacancy> getJavaVacancies(String searchString). Оставь пока метод пустым.

3. Что есть у вакансии?
Название, зарплата, город, название компании, название сайта, на котором вакансия найдена, ссылка на вакансию.
В классе Vacancy создай соответствующие строковые поля: title, salary, city, companyName, siteName, url.

4. Создай геттеры и сеттеры для всех полей класса Vacancy.

5. В пакете model создай класс HHStrategy от Strategy.
Этот класс будет реализовывать конкретную стратегию работы с сайтом ХэдХантер (http://hh.ua/ и http://hh.ru/).


Требования:
1. В корне задачи создай пакет vo, в нем создай класс Vacancy.
2. В классе Provider создай пустой метод getJavaVacancies(String searchString), который будет возвращать список вакансий.
3. В классе Vacancy создай строковые поля: title, salary, city, companyName, siteName, url.
4. К полям в классе Vacancy создай геттеры и сеттеры.
5. В пакете model создай класс HHStrategy, который реализует интерфейс Strategy.

Aggregator (1)
Пришло время немного поработать с информацией в инете. В этом задании ты будешь писать агрегатор java вакансий.
Что у нас должно быть?
Должен быть список сайтов, на которых мы ищем вакансии.
Для начала возьмем http://hh.ua/ и http://hh.ru/, потом уже добавим другие сайты поиска работы.
Это один и тот же сайт, только в разных доменах.

С чего же нужно начать реализацию? Конечно же с метода main : )
1. Создай класс Aggregator с методом main.

2. Создай пакет model, в нем создай класс Provider.
Этот класс будет обобщать способ получения данных о вакансиях.

3. Т.к. для каждого сайта будет выполняться одинаковый сценарий, то будет паттерн Стратегия. Почитай про него в инете на вики.
В пакете model создай интерфейс Strategy.
Он будет отвечать за получение данных с сайта.

4. В класс Provider добавь поле Strategy strategy. Добавь конструктор с этим полем и сеттер.


Требования:
1. Создай класс Aggregator с методом main.
2. Создай пакет model, и в нем создай класс Provider.
3. В пакете model создай интерфейс Strategy.
4. В класс Provider добавь поле Strategy strategy. Добавь конструктор с этим полем и сеттер.
 */
 