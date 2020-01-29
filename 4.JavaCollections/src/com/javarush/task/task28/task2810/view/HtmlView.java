package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.model.Translator;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class HtmlView implements View{

    private final String filePath = "./4.JavaCollections/src/" +
            this.getClass().getPackage().getName().replace('.', '/') + "/vacancies.html";
    private Controller controller;
    Translator translator = new Translator();

    private String cityName;
    public String getCityName() {return cityName;}
    public void setCityName(String cityName) {this.cityName = cityName;}

    private boolean onlyCity;
    public void setOnlyCity(boolean onlyCity){this.onlyCity = onlyCity;}
    public boolean getOnlyCity(){
        if(!onlyCity) return false;
        return onlyCity;
    }

    private String titleName;
    public String getTitleName() {return titleName;}
    public void setTitleName(String titleName) {this.titleName = titleName;}

    private boolean onlyTille;
    public void setOnlyTitle(boolean onlyTitle){this.onlyTille = onlyTitle;}
    public boolean getOnlyTitle(){
        if(!onlyTille) return false;
        return onlyTille;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(){
//        System.out.println("HtmlView 27");
        titleName = "Junior";  //"Developer" "Тестировщик" "Программист" "Инженер" "Engineer"
        cityName = "Киев";

        if(Pattern.matches("[a-z]+",cityName.toLowerCase())){
            setCityName(translator.goTranslate(cityName,"en","ru"));
        }

        setOnlyCity(true);                       // <<<<<<<<----------------  setOnlyCity(false); ----------------- //
        setOnlyTitle(false);                       // <<<<<<<<----------------  setOnlyTitle(false); ----------------- //
        controller.onCitySelect(cityName);
    }

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
//            System.out.println("HtmlView 33test");
        } catch (Exception e) {}
    }

    private void updateFile(String word) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(word);
        writer.close();
    }

    protected Document getDocument() {
        Document doc = null;
        try {
            doc = Jsoup.parse(new File(filePath),"UTF-8");
        } catch (Exception e) {}
        return doc;
    }

    private String getUpdatedFileContent(List<Vacancy> list) {
        Document page = null;
        Element block = null;
        Element elementTemp = null;
        try {
            page = getDocument();
//System.out.println("View 77 \n" +page+"\n");
            elementTemp = page.getElementsByClass("template").first();
            page.getElementsByAttributeValue("class", "vacancy").remove();
            Element copy = elementTemp.clone();
            Node frame = copy.removeClass("template").removeAttr("style");
            for (Vacancy vacancy : list) {
                block = (Element) frame.clone();
                String city = vacancy.getCity();
/*              if(!date.equals("") && !date.contains("хвилин") && !date.contains("годин")
                            && !date.contains("день") && !date.contains("дня") && !date.contains("днів")) continue;
                String tempSity = city.toLowerCase().trim();
                if(Pattern.matches("[a-z]+",city)){
                tempSity = translator.goTranslate(city,"en","ru");
                }
*/
// уточнить по городам
//                if( getOnlyCity() && tempSity != null && (tempSity.charAt(0) != getCityName().charAt(0))) continue; // несовпадения города
                if(city.contains("Санкт-Петербург") || city.contains("Москва") || city.contains("Владивосток") || city.contains("Калининград")
                        || city.contains("Краснодар") || city.contains("Ульяновск") || city.contains("Новосибирск") || city.contains("Екатеринбург")) continue;

// исключить некоторые вакансии:

                String nameVacancy = vacancy.getTitle().toLowerCase().trim();
                if(nameVacancy.contains("такси") || nameVacancy.contains("middle") || nameVacancy.contains("senior") || nameVacancy.contains("android")
                        || nameVacancy.contains("javascript") || nameVacancy.contains("team lead") || getOnlyTitle() && !nameVacancy.contains(titleName)) continue;

                block.select("a").wrap(vacancy.getTitle()).attr("href", vacancy.getUrl());
                block.select("a").append(vacancy.getTitle());

                block.select(".city").first().append(city);
                block.select(".companyName").first().append(vacancy.getCompanyName());

                String salary = vacancy.getSalary();

                if(salary.equals("")) block.select(".salary").first().append(vacancy.getSiteName()
                        .replace("https://","").replace("https://",""));
                else block.select(".salary").first().append(salary);

                block.select(".skills").first().append(vacancy.getSkills());
                elementTemp.before(block.outerHtml());
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("View 140 ничего не найдено");
        }
        if(page.outerHtml() == null){
            block.select("a").wrap("искать")
                    .attr("href",
                            "https://www.bing.com/search?q=вакансии" +
                                    "+java+киев&go=Отправить&qs=n&form=QBLH&sp=-1&pq=вакансии+java+ки&sc=0-16&sk=&cvid=EB8AE96C426E45759236336CA3B25F40");
            block.select("a").append("По Вашему");
            block.select(".city").first().append("ничего");
            block.select(".companyName").first().append("к сожалению");
            block.select(".salary").first().append("запросу");
            block.select(".skills").first().append("не найдено");
            elementTemp.before(block.outerHtml());

        }
 //System.out.println("-------- View 140 ---------------------------------- \n" +page.outerHtml());
        return page.outerHtml();
    }
}










/*
2. Получи элемент, у которого есть класс template.
Сделай копию этого объекта, удали из нее атрибут "style" и класс "template".
Используй этот элемент в качестве шаблона для добавления новой строки в таблицу вакансий.

3. Удали все добавленные ранее вакансии. У них единственный класс "vacancy".
В файле backup.html это одна вакансия - Junior Java Developer.
Нужно удалить все теги tr, у которых class="vacancy".
Но тег tr, у которого class="vacancy template", не удаляй.
Используй метод remove.

4. В цикле для каждой вакансии:
4.1. склонируй шаблон тега, полученного в п.2. Метод clone.
4.2. получи элемент, у которого есть класс "city". Запиши в него название города из вакансии.
4.3. получи элемент, у которого есть класс "companyName". Запиши в него название компании из вакансии.
4.4. получи элемент, у которого есть класс "salary". Запиши в него зарплату из вакансии.
4.5. получи элемент-ссылку с тегом a. Запиши в него название вакансии(title). Установи реальную ссылку на вакансию вместо href="url".
4.6. добавь outerHtml элемента, в который ты записывал данные вакансии,
непосредственно перед шаблоном <tr class="vacancy template" style="display: none">

5. Верни html код всего документа в качестве результата работы метода.

6. В случае возникновения исключения, выведи его стек-трейс и верни строку "Some exception occurred".

7. Запусти приложение, убедись, что все вакансии пишутся в файл vacancies.html.

Требования:
1. В классе HtmlView добавь метод protected Document getDocument() в котором распарси файл vacancies.html используя Jsoup.

2. Реализуй метод getUpdatedFileContent(). Для начала, получи распарсеную страницу с помощью метода getDocument().
3. Получи элемент, у которого есть класс template. Сделай копию этого объекта, удали из нее атрибут "style" и класс "template".
4. Удали из страницы все добавленные ранее вакансии с классом "vacancy". Элемент с классом "vacancy template" должен остаться.
5. Перед объектом template для каждой вакансии добавь на страницу отдельный html-элемент, используя копию template.
Верни html-код всей страницы в качестве результата работы метода.
6. Для каждой вакансии должен быть корректно заполнен элемент-ссылка с названием вакансии(title) и http-ссылкой на нее(href).
7. Для каждой вакансии должен быть корректно заполнен элемент с классом "city".
8. Для каждой вакансии должен быть корректно заполнен элемент с классом "companyName".
9. Для каждой вакансии должен быть корректно заполнен элемент с классом "salary".
10. В случае возникновения исключения, выведи его стек-трейс и верни строку "Some exception occurred".

Aggregator (15)
В классе HtmlView остался один пустой метод getUpdatedFileContent. В этом задании я опишу, что он должен делать.

1. В HtmlView создай protected метод Document getDocument() throws IOException, в котором
распарси файл vacancies.html используя Jsoup. Кодировка файла "UTF-8", используй поле filePath.




Aggregator (14)
В классе HtmlView остались два пустых метода.
В этом задании я опишу, что должен делать метод updateFile. А также расскажу, как можно дебажить.

Он принимает тело файла в виде строки. Нужно его записать в файл, который находится по пути filePath.
Ты это хорошо умеешь делать, поэтому подробностей тут не будет.

Теперь - как дебажить.
Поставь breakpoint, запусти в дебаг моде.
нажми Alt+F8 (Run -> Evaluate Expression).
В выражении (верхняя строка) напиши System.out.println("AAA") и нажми Alt+V (снизу кнопка Evaluate).
Перейди в окно консоли, там вывелось "AAA".

Используя это окно ты можешь смотреть текущие данные, заменять их на другие, нужные тебе.
Можешь выполнять все, что хочешь. Например, удалить все элементы мапы, изменить значение любой переменной, присвоить новое значение либо обнулить ее.
Поставь брекпоинт в Controller.onCitySelect, запусти в дебаг моде.
Остановились на этом брекпоинте? Отлично, жми Alt+F8, сверху в строке cityName = "junior";
далее жми Alt+V и F9, чтобы продолжить работу приложения.
Список вакансий, который пришел в HtmlView.update, выполнялся для запроса "java junior".
Используй окно Expression Evaluation, думаю, оно тебе понадобится в следующем пункте.


Требования:
1. В классе HtmlView в методе updateFile открой поток для записи в файл.
2. Запиши в файл данные, которые метод updateFile получает аргументом.
3. Закрой поток записи в файл.
Aggregator (13)
Смотри, в пакете view появились два новых файла:
vacancies.html - будешь в него записывать данные,
backup.html - дубликат vacancies.html для восстановления, вдруг данные в vacancies.html сотрутся.

Стань слева в дереве проекта на vacancies.html, нажми правой клавишей мыши, далее "Open in Browser".
Так будешь смотреть результат своего парсинга.

Пора заняться отображением вакансий.
1. В методе update класса HtmlView реализуй следующую логику:

1.1. сформируй новое тело файла vacancies.html, которое будет содержать вакансии,
1.2. запиши в файл vacancies.html его обновленное тело,
1.3. Все исключения должны обрабатываться в этом методе - выведи стек-трейс, если возникнет исключение.

2. Для реализации п.1 создай два пустых private метода:
String getUpdatedFileContent(List<Vacancy>), void updateFile(String),
Реализовывать их логику будем в следующих заданиях.

3. Чтобы добраться до файла vacancies.html, сформируй относительный путь к нему.
В классе HtmlView создай приватное строковое final поле filePath, присвой ему относительный путь к vacancies.html.
Путь должен быть относительно корня проекта.
Формируй путь динамически используя this.getClass().getPackage() и разделитель "/".

Подсказка: путь должен начинаться с "./".


Aggregator (12)
Чтобы запустить приложение, нужно эмулировать событие пользователя:
1. В классе HtmlView создай публичный метод void userCitySelectEmulationMethod().
Пусть этот метод пробросит вызов в контроллер для города "Odessa".

2. Чтобы понять, что все работает, выведи что-то в консоль в методе update в HtmlView, например, количество вакансий.
Это будет флаг, что можно двигаться дальше.

3. Для запуска нужно еще обновить метод main в Aggregator.
3.1. Создай вью, модель, контроллер.
3.2. Засэть во вью контроллер.
3.3. Вызови у вью метод userCitySelectEmulationMethod.

4. Запускай приложение! Подожди несколько секунд, чтобы выгреблись данные.
Работает? Отлично, что работает!


Требования:
1. В классе HtmlView создай и реализуй публичный метод void userCitySelectEmulationMethod() согласно заданию.
2. В классе HtmlView в метод update добавь вывод количества вакансий в консоль.
3. Реализуй метод main согласно заданию и проверь, работает ли приложение как положено.


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

//        System.out.println(elementTemp.toString().replaceAll("<\\/?(<?tr>?)\\w*[^>]*>", "").trim() +"\n");
