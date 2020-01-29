package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JabsStrategy implements Strategy{

    private static final String URL_FORMAT = "https://jobs.dou.ua/vacancies/?city=%s&search=Java";

    public JabsStrategy() throws IOException {
//System.out.println("JabsStrategy 21 ");
    }

    protected Document getDocument(String searchString) {

        String url = String.format  (URL_FORMAT, searchString);
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15")
                    .referrer("")
                    .get();

        } catch (IOException e) {
            System.out.println("JabsStrategy 31 ошибка connect");
        }
//System.out.println("JabsStrategy 33 ");
        return document;
    }

    @Override     //  должен получать содержимое страниц с помощью метода getDocument. Начни с 0 страницы.
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> list = new ArrayList<>();
            Document document = getDocument(searchString);
            Elements elements = null;
            try {
//                System.out.println("JabsStrategy 43 document\n"+document);

                elements = document.getElementsByClass("l-vacancy __hot");
//System.out.println("JabsStrategy 46 -------------------------------------------------------------\n"+ elements);

            } catch (Exception e) {
                System.out.println("JabsStrategy 48 ошибка select");
            }
            if(elements.size() != 0) {
                elements.forEach(element -> {
                    if (element != null) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setSiteName("https://jobs.dou.ua");
                        vacancy.setUrl(element.getElementsByTag("a").first().attr("href").trim());
                        vacancy.setTitle(element.getElementsByTag("a").first().text().trim());
                        vacancy.setCompanyName(element.getElementsByTag("a").last().text().trim());
                        vacancy.setCity(element.getElementsByClass("cities").text().trim());
                        vacancy.setSalary(element.getElementsByClass("salary").text().trim());
                        vacancy.setSkills(element.getElementsByClass("sh-info").text().trim());
                        list.add(vacancy);
//System.out.println(element.getElementsByClass("sh-info").text().trim());


//System.out.println(element.getElementsByClass("skills").text().trim());

                    }
//System.out.println("test");
                });
            }
        return list;
    }
}