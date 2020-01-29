package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UAJoobleStrategy implements Strategy {        // !!!   сайт по многим странам и городам  !!!!!!!!!!

    private static final String URL_FORMAT = "https://ua.jooble.org/робота-java-junior/%s?p=%d";

    protected Document getDocument(String searchString, int page) {
        String url = String.format(URL_FORMAT, searchString, page);
        Document document = null;
        try {
//System.out.println("UAJoobleStrategy 21 url = " + url);
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
        while (true) {
            Document doc = getDocument(searchString, page);
//System.out.println("UAJoobleStrategy 36 -------------------------------------------------------------\n" + doc);

            Elements elements = null;

            try {
                elements = doc.getElementsByAttribute("data-sgroup");
//System.out.println("RabotaStrategy 43 -------------------------------------------------------------\n"+ elements);
            } catch (Exception e) {
                System.out.println("UAJoobleStrategy 44 ошибка doc");
                return list;
            }

            if (elements.size() == 0) break;
            List line = new ArrayList(elements);
            for (Element element : elements) {
//System.out.println("RabotaStrategy 50 element " +element != null);
                if (element != null) {
                    Vacancy vacancy = new Vacancy();
                    String date = element.getElementsByClass("date_location").first().text().trim();
// System.out.print(date+" ");
//System.out.println((!date.contains("годин") && !date.contains("дн")) +"\n\n");
//System.out.println(element+"\n\n");

                    if(!date.contains("хвилин") && !date.contains("годин")
                            && !date.contains("день") && !date.contains("дня") && !date.contains("днів")
                            && !date.contains("місяць") && !date.contains("місяця")) continue;
                    String title = element.getElementsByClass("position").tagName("span").text().trim();//
//                    if(!title.toLowerCase().contains("java")) continue;
//System.out.println("title = "+title);
                    vacancy.setTitle(title);//
                    vacancy.setSiteName("https://ua.jooble.org");
                    vacancy.setUrl(element.getElementsByClass("link-position job-marker-js").attr("href").trim());
                    vacancy.setTitle(element.getElementsByClass("position").tagName("span").text().trim());//

                    vacancy.setCompanyName(element.getElementsByClass("company-name").text().trim()); //
                    vacancy.setCity(element.getElementsByClass("date_location__region").text().trim());//
                    vacancy.setSalary(element.getElementsByClass("salary").text());            //
                    vacancy.setSkills(element.getElementsByClass("description").text().trim());       //
                    list.add(vacancy);
//                    System.out.println("Jooble 74 "+ element.getElementsByClass("salary").text());
                }
            }
            if(page < 20) page++;
            else break;
        }
//for(Vacancy a : list)System.out.println(a+"\n");
        return list;
    }

}