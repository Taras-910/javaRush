package com.javarush.task.task28.task2810.model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Translator {
//// Pattern.matches("[a-z]+",test1.toLowerCase())
    private static final String URL_TRANSLATER = "https://m.translate.ru/dictionary/%s-%s/%s";
    public String goTranslate(String word, String from, String to){
        String url = String.format(URL_TRANSLATER, from, to, word);
//System.out.println("Translator 11 "+ url);
        Document document = null;
        String translatedWord = "";
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Safari/605.1.15")
                    .referrer("")
                    .get();
//System.out.println("------------------------------------------------------\n"+ document);
            translatedWord = document.getElementsByAttributeValue("class","ref_result").text().split(" ")[0];
        } catch (Exception e) {System.out.println("Translator 22 ошибка translate");}
//        System.out.println(translatedWord);
// for(Element el :elements) System.out.println("------------------------------------------------------\n"+ el);
        return translatedWord;
    }
}
