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

public class UAIndeedStrategy implements Strategy{

    //   https://ua.indeed.com/jobs?q=Java+Developer&l=Киев&start=20               //   !!!   page*10
    //   https://ua.indeed.com/jobs?q=Java+Developer&l=%s&start=%d0               //   !!!   page*10
    Translator tr = new Translator();
    private static final String URL_FORMAT = "https://ua.indeed.com/jobs?q=Java+Developer&l=%s&start=%d0";

    protected Document getDocument(String searchString, int page) {
        String city = searchString;
        if(Pattern.matches("[a-z]+",searchString.toLowerCase())){
            city = (tr.goTranslate(searchString,"en","ru"));
        }
        String url = String.format(URL_FORMAT, city, page);
        Document document = null;
        try {
//System.out.println("Indeed 21 url = " + url);
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
//System.out.println("Indeed 36 -------------------------------------------------------------\n" + doc);

            Elements elements = null;

            try {
                elements = doc.getElementsByClass("jobsearch-SerpJobCard unifiedRow row result");
//System.out.println("Indeed 43 -------------------------------------------------------------"+elements.size()+"\n");
//for(Element el : elements) System.out.println(el+"\n\n");
            } catch (Exception e) {
                System.out.println("Indeed 44 ошибка doc");
                return list;
            }

            if (elements.size() == 0) break;
            List line = new ArrayList(elements);
            for (Element element : elements) {
//System.out.println("Indeed 50 element " +element != null);
                if (element != null) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName("https://ua.indeed.com");

                    vacancy.setTitle(element.getElementsByAttributeValue("data-tn-element","jobTitle").text().trim());
                    vacancy.setSalary(element.getElementsByClass("salary no-wrap").text());
                    vacancy.setCity(element.getElementsByClass("location accessible-contrast-color-location").text().trim());
                    vacancy.setCompanyName(element.getElementsByClass("company").text().trim());
                    vacancy.setSkills(element.getElementsByClass("summary").text().trim());
                    vacancy.setUrl("https://ua.indeed.com/вакансии?q=Java%20Developer");//  ??????

//System.out.println("\n\nIndeed 71 -----------------------------------------------------------\n"+element);
                    list.add(vacancy);
                                //https://ua.indeed.com/вакансии?q=Java%20Developer&l=Киев&start=00&advn=4649899438200304&vjk=bed3b63c3150a2e3
                                          //https://ua.indeed.com/viewjob?jk=bed3b63c3150a2e3&from=tp-serp&tk=1djcicvkg97ci802
                    //            https://ua.indeed.com/описание-вакансии?jk=bed3b63c3150a2e3
//System.out.println("\nIndeed 74 "+ element.getElementsByAttributeValue("data-tn-element","jobTitle").text().trim());
                }
            }
            if(page < 10) page++;
            else break;
        }
//for(Vacancy a : list)System.out.println(a+"\n");
        return list;
    }

}