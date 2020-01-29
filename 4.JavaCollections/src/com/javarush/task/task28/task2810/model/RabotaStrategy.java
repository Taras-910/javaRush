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

public class RabotaStrategy implements Strategy{
    private static final String URL_FORMAT = "https://rabota.ua/ua/zapros/java#";
//// Pattern.matches("[a-z]+",test1.toLowerCase())
    Translator tr = new Translator();
    public String enterSity;
    public String getEnterSity() {return enterSity;}
    public void setEnterSity(String enterSity) {this.enterSity = enterSity;}

    protected Document getDocument(String searchString, int page){
        if(Pattern.matches("[a-z]+",searchString.toLowerCase())){
            setEnterSity(tr.goTranslate(searchString,"en","ru"));
        }
//System.out.println("RabotaStrategy 26 "+ getEnterSity());
        String url = String.format(URL_FORMAT);
        Document document = null;
        try {
//System.out.println("RabotaStrategy 21 url = "+url);
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
//System.out.println("RabotaStrategy 36 -------------------------------------------------------------\n"+ doc);
            Elements elements = null;
            try {
                elements = doc.getElementsByClass("f-vacancylist-vacancyblock");
//System.out.println("RabotaStrategy 43 -------------------------------------------------------------\n"+ elements);
            } catch (Exception e) {
                System.out.println("RabotaStrategy 44 ошибка doc");
                return list;
            }
            if (elements.size() == 0) break;
            List line = new ArrayList(elements);
            for(Element element : elements) {
//System.out.println("RabotaStrategy 50 element " +element != null);
                if (element != null) {
                    Vacancy vacancy = new Vacancy();
                    String date = element.getElementsByClass("f-vacancylist-agotime f-text-light-gray fd-craftsmen").text().trim();
// System.out.print(date+" ");
//System.out.println(!date.equals("") && !date.contains("хвилин")
// &&! date.contains("годин") && !date.contains("день") && !date.contains("дня") && !date.contains("днів"));
//System.out.println(element+"\n\n");
/*
                    if(!date.equals("") && !date.contains("хвилин") && !date.contains("годин")
                            && !date.contains("день") && !date.contains("дня") && !date.contains("днів")) continue;
*/
                    vacancy.setSiteName("https://rabota.ua");
                    vacancy.setUrl(vacancy.getSiteName().concat(element.getElementsByClass("f-visited-enable ga_listing")
                            .attr("href").trim()));
                    vacancy.setTitle(element.getElementsByClass("f-visited-enable ga_listing").text().trim());
                    vacancy.setCompanyName(element.getElementsByClass("fd-merchant").first().text().trim());
                    String city = element.select("p[class=fd-merchant]").text().trim();

//System.out.println(city+" "+getEnterSity());
/*
                    if(city != null && city.charAt(0) != getEnterSity().charAt(0)) continue;
*/
                    vacancy.setCity(city);
                    vacancy.setSalary(element.getElementsByClass("salary").text().trim());
                    vacancy.setSkills(element.getElementsByClass("f-vacancylist-shortdescr f-text-gray fd-craftsmen").text().trim());
                    list.add(vacancy);
//System.out.println(element.getElementsByClass("f-vacancylist-agotime f-text-light-gray fd-craftsmen").text().trim());
//System.out.println(element.getElementsByClass("f-text-dark-bluegray f-visited-enable").first().text().trim());
//System.out.println();
                }
            }
            page++;
            if(page > 0) break;
        }
//for(Vacancy a : list)System.out.println(a+"\n");
        return list;
    }
}
