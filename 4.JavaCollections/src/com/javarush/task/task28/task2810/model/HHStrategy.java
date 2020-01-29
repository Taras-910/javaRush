package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy{

    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    public HHStrategy() throws IOException {
//System.out.println("HHStrategy 21 ");
    }

    protected Document getDocument(String searchString, int page) {

        String url = String.format  (URL_FORMAT, searchString, page);
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15")
                    .referrer("")
                    .get();

        } catch (IOException e) {
            System.out.println("HHStrategy 31 ошибка connect");
        }
//System.out.println("Strategy 33 ");
        return document;
    }

    @Override     //  должен получать содержимое страниц с помощью метода getDocument. Начни с 0 страницы.
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;
        while (true){
            Document document = getDocument(searchString, page);
            Elements elements = null;
            try {
                elements = document.select("[data-qa=vacancy-serp__vacancy]");
//System.out.println("HHStrategy 46 -------------------------------------------------------------\n"+ elements);

            } catch (Exception e) {
                System.out.println("HHStrategy 48 ошибка select");
            }
            if(elements.size() == 0)break;
            elements.forEach(element -> {
                if (element != null){
                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName("http://hh.ua");
                    vacancy.setUrl(element.getElementsByTag("a").first().attr("href").trim());
                    vacancy.setTitle(element.getElementsByTag("a").first().text().trim());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text().trim());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text().trim());
                    vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text().trim());
                    vacancy.setSkills(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_requirement").text().trim());
                    list.add(vacancy);
//System.out.println(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy_snippet_requirement").text());
//                    System.out.println();
                }
            });
            page++;
        }
        return list;
    }
}
/*
   <tr class="b-vacancy-list-standard" data-qa="vacancy-serp__vacancy">

Aggregator (8)
Запусти снова программу в дебаг моде.
Скопируй полученное значение document.html() в созданный ранее html файл.
Отформатируй его и найди теги с вакансиями.

Почитай в Сообществе дополнительный материал к лекции про селекторы атрибута.
ВНИМАНИЕ: ОСОБЕННОСТИ ТЕСТИРОВАНИЯ!
HTML код странички ХэдХантера может меняться, чтобы эта задача продолжила работать стабильно не меняя тесты воспользуйся
закешированной версией http://javarush.ru/testdata/big28data.html

Это только для этого пункта, в следующих заданиях используй реальные страницы.

1. В классе HHStrategy создай protected метод Document getDocument(String searchString, int page) throws IOException.

2. Реализуй следующую логику метода getVacancies в классе HHStrategy:
2.1. Приконнекться к закешированной страничке ХэдХантера используя метод getDocument, нумерация начинается с 0.
2.2. Получи список элементов с атрибутом "vacancy-serp__vacancy". Должно быть до 20 вакансий на странице.
2.3. Если данные в списке из п.2.2 есть, то для каждого элемента:
2.3.1. создать вакансию и заполнить все ее данные, получив данные из текущего элемента.
Если тег с зарплатой присутствует, то заполнить и поле salary, иначе инициализировать поле пустой строкой.
2.4. Выполнить п.2.1-2.3 для следующей страницы ХэдХантера.
2.5. Если закончились страницы с вакансиями, то выйти из цикла.

Исключения игнорировать.
Все вакансии добавить в общий список и вернуть в качестве результата метода.

Подсказка по зарплате:
Поиграйся с URL_FORMAT, добавь туда еще один параметр, чтобы получить вакансии с зарплатами.
Проанализируй полученный html и найди тег для зарплаты.
Не забудь потом вернуть значение URL_FORMAT обратно.

Требования:
1. В классе HHStrategy создай protected метод getDocument(String searchString, int page).
Перенеси туда логику по получению объекта html-страницы Document.
2. Метод getVacancies класса HHStrategy должен получать содержимое страниц с помощью метода getDocument. Начни с 0 страницы.
3. Из объекта Document получи список html-элементов с атрибутом "vacancy-serp__vacancy".
Для каждого элемента создай объект вакансии и добавь его в возвращающий методом список.
4. Нужно последовательно обработать все страницы результатов поиска. Как только страницы с вакансиями закончатся,
прерви цикл и верни список найденных вакансий.
5. У каждой вакансии должно быть заполнено поле title полученными из html-элемента данными о названии вакансии.
6. У каждой вакансии должно быть заполнено поле url полученной из html-элемента ссылкой на вакансию.
7. У каждой вакансии должно быть заполнено поле city полученными из html-элемента данными о городе.
8. У каждой вакансии должно быть заполнено поле companyName полученными из html-элемента данными о компании.
9. У каждой вакансии должно быть заполнено поле siteName значением сайта, на котором вакансия была найдена.
10. Поле salary у вакансии должно быть заполнено, если в html-элементе присутствовал тег с зарплатой.
Иначе поле должно быть инициализировано пустой строкой.
11. Если ты менял значение поля URL_FORMAT, не забудь вернуть его обратно.



Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15
    Elements metaElements = doc.select("meta");
    String userAgent = metaElements.attr("name");

getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy")

Для потомков от себя добавлю:
1.Не надо ничего кэшировать и открывать с диска, не тратьте время.
Коннектититесь по исходной ссылке к HH:
String.format(URL_FORMAT, searchString, page);

валидатору пофиг на ваши кэшированные файлы, только ошибок огребете.
2. В цикле нужно обойти все страницы начиная с 0, пока кол-во элементов на ней > 0.
3. Никаких доп проверок у зарплаты не надо делать, если её нет, в любом случае вернется пустая строка.
select().first() лучше чем selectFirst(), text() лучше чем html().





Aggregator (7)
Чтобы Хэдхантер знал, кто к нему коннектится, проставим Request Headers в наш запрос.

Для разработчиков созданы инструменты, которые показывают различную информацию про запросы.
Я расскажу тебе про два инструмента.

***Chrome****
1. В браузере Хром пойди в Меню - Инструменты - Инструменты разработчика, или нажми Ctrl+Shift+I
2. В браузерной строке набери URL http://hh.ua/search/vacancy?text=java+киев
3. Перейди на табу Network(Сеть), запрашиваемый URL должен быть в самом верху.
У него метод отправки данных GET (еще есть POST), статус 200(успешно)
4. Выбери его, откроется информация о страничке.
5. Найди Request Headers(Заголовки запроса)

***FireFox***
1. Для FireFox есть плагин FireBug. Ставь FireBug (Меню - Дополнения - Поиск - FireBug, установить).
2. Правой клавишей мыши - Инспектировать элемент с помощью FireBug. Перейди на табу Net(Сеть), подменю HTML.
3. В браузерной строке набери URL http://hh.ua/search/vacancy?text=java+киев
4. В подменю HTML появился список запросов включая набранный в браузерной строке. Нажми на нем и зайди в меню "Заголовки"
5. Найди Request Headers(Заголовки запроса)

Добавь в коннекшен к урлу Хедхантера userAgent и referrer.

Требования:
1. В методе getVacancies класса HHStrategy после создания коннекшена добавь заголовок userAgent.
2. В методе getVacancies класса HHStrategy после создания коннекшена добавь заголовок referrer.


Aggregator (6)
1. Слева в панеле Project найди снизу External Libraries. В jsoup найди пакет examples, посмотри классы в этом пакете.

2. По аналогии с реализацией в примерах кода jsoup - реализуй коннекшен к урлу ХэдХантера методом GET.

Это нужно сделать в методе getVacancies класса HHStrategy.

Подсказка: получится объект класса Document.

3. Поставь брекпоинт сразу после коннекшена. Запусти программу в дебаг моде.

Скопируй полученное значение document.html() в буфер.

4. Создай файл с расширением html где-то в корне проекта.

Вставь содержимое буфера в этот файл и отформатируй его Ctrl+Alt+L, IDEA умеет форматировать HTML.

Ура! Это код страницы HTML :)

5. Реализуй в вакансии (Vacancy) методы equals/hashCode.

Alt+Enter - equals() and hashCode().

Требования:
1. В методе getVacancies класса HHStrategy реализуй коннекшен к урлу ХэдХантера методом GET.
2. Скопируй html код полученной странички себе в файл для дальнейшего анализа (проверка этого требования не выполняется).
3. В классе Vacancy сгенерируй с помощью IDE методы equals() и hashCode().



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



Aggregator (4)
Открой сайт ХэдХантер - http://hh.ua/ или http://hh.ru/
В строке поиска набери "java Киев", снизу перейди на вторую страницу, т.к. урлы часто отличаются на первой странице и далее.
У меня получилась такая ссылка:
http://hh.ua/search/vacancy?text=java+Киев&page=1


Из этого следует, что
1) если тебе нужно будет фильтровать по городу, то ты добавишь его после слова java, разделив их знаком "+",
2) нумерация страниц начинается с 0.

Итак, ссылка будет примерно такой:

http://hh.ua/search/vacancy?text=java+ADDITIONAL_VALUE&page=PAGE_VALUE

1. Из полученной ссылки в HHStrategy создай приватную строковую константу URL_FORMAT, которая будет передаваться в String.format.
String.format(URL_FORMAT, "Kiev", 3) должно равняться
"http://hh.ua/search/vacancy?text=java+Kiev&page=3"
или
"http://hh.ru/search/vacancy?text=java+Kiev&page=3"
Для этого скопируйте ссылку в код и нажмите на ней нужную комбинацию клавиш.
Ctrl+Alt+C(Constant) - создание констант,
Ctrl+Alt+M(Method) - создание методов,
Ctrl+Alt+V(Variable) - создание переменных.

2. Тебе нужно программно получить исходный код страницы. Это HTTP запрос. Тебе понадобится Java HTML Parser.
Хороший парсер jsoup, будешь использовать его.
Найди сайт jsoup - Java HTML Parser, скачай с него либу версии и 1.9.2 и ее сорцы(sources). Класть их внутрь проекта не нужно!

3. Подключи новые либы:
В IDEA открой Project Structure (в меню File).
Слева выбери Project Settings -> Libraries, в окошке справа сверху нажми "+".
Выбери "Java", если либы еще не нет, либо "Attach Files or Directories", чтоб добавить джарники к существующей либе.
В диалоговом окне открой путь к папке из п.2, открой саму папку и выбери все либы - *.jar файлы.

4. Прочитай дополнительный материал к лекции в Сообществе.

Требования:
1. В классе HHStrategy создай приватную строковую константу URL_FORMAT.
2. Результат команды String.format(URL_FORMAT, String, int) должен генерироваться согласно заданию.
3. Для выполнения дальнейших указаний, подключи библиотеку jsoup версии 1.9.2 (проверка этого требования не выполняется).



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

 */

/*
    URL url = new URL(URL_FORMAT);
    InputStream inputStream = url.openStream();

    Path Filehtml2 = Files.createFile(Paths.get("/Users/taras/Downloads/JavaRushTasks/4.JavaCollections"));

    long s = Files.copy(inputStream, Filehtml2);

 */