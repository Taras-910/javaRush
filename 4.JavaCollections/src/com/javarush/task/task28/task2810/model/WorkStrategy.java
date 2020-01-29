package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class WorkStrategy implements Strategy{
    private static final String URL_FORMAT = "https://www.work.ua/ru/jobs-%s-программист+java/?page=%d";
    protected Document getDocument(String searchString, int page){
System.out.printf(URL_FORMAT, searchString, page);
        String url = String.format  (URL_FORMAT, searchString, page);
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15")
                    .referrer("")
                    .get();
        } catch (IOException e) {
            System.out.println("Work 25 ошибка  java.lang.NullPointerException");
        }
        return document;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        Translator tr = new Translator();
        if(!Pattern.matches("[a-z]+",searchString.toLowerCase())) searchString = tr.goTranslate(searchString,"ru","en");
        List<Vacancy> list = new ArrayList<>();
        int page = 0;
        while(true) {
            Document doc = getDocument(searchString, page);
            Elements elements = null;
//System.out.println("WorkStrategy 43 -------------------------------------------------------------\n"+ doc);
            try {
                elements = doc.getElementsByClass("card card-hover card-visited wordwrap job-link");
//System.out.println("WorkStrategy 43 -------------------------------------------------------------");
//for(Element el : elements) System.out.println( el+"\n");
                if (elements.size() == 0) break;
                List line = new ArrayList(elements);
                for(Element element : elements) {
//System.out.println("WorkStrategy 50 element " +element != null);
                    if (element != null) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setSiteName("https://www.work.ua");
                        vacancy.setUrl(vacancy.getSiteName().concat(element
                                .getElementsByTag("a").attr("href")));
                        vacancy.setTitle(element.getElementsByClass("card card-hover card-visited wordwrap job-link")
                                .first().getElementsByAttribute("title").text());

                        vacancy.setCompanyName(element.getElementsByTag("b").first().text());
                        vacancy.setCity(element.getElementsByClass("label label-vip ").next().next().text().trim().replace(" ·",""));
                        vacancy.setSalary(element.getElementsByClass("salary").text().trim());
                        vacancy.setSkills(element.getElementsByClass("overflow").text().trim());
                        vacancy.setDate(element.getElementsByTag("a").attr("title").split(",")[1]);
//System.out.println("Work 61 "+ element.getElementsByClass("card card-hover card-visited wordwrap job-link").tagName("a").text().trim());
                        list.add(vacancy);
                    }
                }
            } catch (Exception e) {
                System.out.println("Work 66 ошибка  java.lang.NullPointerException");
                return list;
            }
            page++;
        }
//for(Vacancy a : list)System.out.println(a+"\n");
        return list;
    }
}