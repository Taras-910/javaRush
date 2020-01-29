package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy{

    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";

    protected Document getDocument(String searchString, int page){
        String url = String.format  (URL_FORMAT, searchString, page);
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15")
                    .referrer("")
                    .get();
        } catch (IOException e) {
        }
        return document;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;
        while(true) {
            Document doc = getDocument(searchString, page);
            Elements elements = null;

            try {
                elements = doc.getElementsByClass("job");
//System.out.println("MoikrugStrategy 43 -------------------------------------------------------------\n"+ elements);
            } catch (Exception e) {
                e.printStackTrace();
            }
//Из объекта Document получи список html-элементов с вакансиями. Для каждого элемента создай объект вакансии и добавь его в возвращающий методом список
            if (elements.size() == 0) break;
            List line = new ArrayList(elements);
            for(Element element : elements) {
//System.out.println("MoikrugStrategy 50 element " +element != null);
                if (element != null) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName("https://moikrug.ru");
                    vacancy.setUrl(vacancy.getSiteName().concat(element.getElementsByClass("job_icon").attr("href").trim()));
                    vacancy.setTitle(element.getElementsByClass("title").tagName("a").text().trim());

                    vacancy.setCompanyName(element.getElementsByClass("company_name").text().trim());
                    vacancy.setCity(element.getElementsByClass("location").text().trim());
                    vacancy.setSalary(element.getElementsByClass("salary").text().trim());
                    vacancy.setSkills(element.getElementsByClass("skills").text().trim());
                    list.add(vacancy);

//System.out.println(element.getElementsByClass("skills").text().trim());
                }
            }
            page++;
        }
//for(Vacancy a : list)System.out.println(a+"\n");
        return list;
    }
}
/*
Aggregator (16)
Требования:
1. В пакете model создай новый класс MoikrugStrategy, который реализует интерфейс Strategy.
2. В классе MoikrugStrategy добавь приватную статическую константу URL_FORMAT, по аналогии с HHStrategy.
3. В классе MoikrugStrategy создай protected метод getDocument(String searchString, int page). Реализуй его по аналогии с HHStrategy.

4. Метод getVacancies класса MoikrugStrategy должен получать содержимое страниц с помощью метода getDocument. Начни с 0 страницы.
5. Из объекта Document получи список html-элементов с вакансиями.
Для каждого элемента создай объект вакансии и добавь его в возвращающий методом список.
6. Нужно последовательно обработать все страницы результатов поиска. Как только страницы с вакансиями закончатся,
прерви цикл и верни список найденных вакансий.
7. У каждой вакансии должно быть заполнено поле title полученными из html-элемента данными о названии вакансии.
8. У каждой вакансии должно быть заполнено поле url полученной из html-элемента ссылкой на вакансию.
9. У каждой вакансии должно быть заполнено поле companyName полученными из html-элемента данными о компании.
10. У каждой вакансии должно быть заполнено поле siteName значением сайта, на котором вакансия была найдена.
11. Поле city у вакансии должно быть заполнено, если в html-элементе присутствовал тег с данными о городе.
Иначе поле должно быть инициализировано пустой строкой.
12. Поле salary у вакансии должно быть заполнено, если в html-элементе присутствовал тег с зарплатой.
Иначе поле должно быть инициализировано пустой строкой.
13. В методе main в модель добавь новый провайдер, инициализированный стратегией MoikrugStrategy.

 Ты молодец, большая работа позади! Теперь тебе легко будет мониторить вакансии для трудоустройства :)
Сейчас Aggregator использует только одну стратегию сбора вакансий - с ХэдХантера.
1. По аналогии с HHStrategy добавь стратегию для Мой круг.
Назови класс MoikrugStrategy, реализуй метод getVacancies.
Вот тебе пример ссылки:
https://moikrug.ru/vacancies?q=java+Dnepropetrovsk
Пример ссылки на вакансию:
https://moikrug.ru/vacancies/560164256

2. В методе main создай провайдер для MoikrugStrategy. Передай этот провайдер в конструктор Model.
Это удобно сделать, т.к. модель принимает много провайдеров.
Остальную логику менять не нужно. Видишь, как легко расширять функционал?
От правильной архитектуры зависит многое.

ВНИМАНИЕ: ОСОБЕННОСТИ ТЕСТИРОВАНИЯ!
HTML код странички c вакансиями с Моего круга, как и ХэдХантера, может меняться. Чтобы эта задача прошла тестирование,
при реализации задания воспользуйся закешированной версией страницы: http://javarush.ru/testdata/big28data2.html.
Это необходимо для тестирования данного задания, после его сдачи проверь работу MoikrugStrategy на реальном сайте.

*/