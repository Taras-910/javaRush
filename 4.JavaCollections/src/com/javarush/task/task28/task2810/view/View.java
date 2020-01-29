package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.util.List;

public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
/*
Пришло время чуток порефакторить код.
Почитай в инете про паттерн MVC(Model-View-Controller).
Кратко - используя View(вид) пользователь генерирует события, которые обрабатывает контроллер.
Контроллер принимает решение, какие данные ему нужны, и обращается к нужной моделе.
Модель получает данные, например, из БД или из URL-а. Потом модель передает данные во View.
View отображает данные.

1. Создай пакет view, в котором создай интерфейс View с двумя методами:
void update(List<Vacancy> vacancies);
void setController(Controller controller);

2. В пакете view создай класс HtmlView от View.

3. В классе HtmlView создай поле контроллер, используй его для реализации одного из методов интерфейса.


Требования:
1. В корне задачи создай новый пакет view, в нем создай интерфейс View.
2. Интерфейс View должен содержать два метода согласно заданию.
3. В пакете view создай новый класс HtmlView, который реализует View.
4. В классе HtmlView создай поле контроллер и для его инициализации реализуй один из методов интерфейса.
 */