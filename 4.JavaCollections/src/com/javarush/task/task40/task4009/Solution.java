package com.javarush.task.task40.task4009;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) throws ParseException {

        System.out.println(getWeekdayOfBirthday("3.6.1980", "2019"));
    }

    public static String getWeekdayOfBirthday(String birthday, String year) throws ParseException {
        //напишите тут ваш код
        LocalDate localDate = null;
        String old = birthday;

        SimpleDateFormat oldFormat = new SimpleDateFormat("dd.mm.yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ITALY);
        String result = newFormat.format(oldFormat.parse(old));

        localDate = LocalDate.parse(result);

        String dayOfManth = localDate.get(DAY_OF_MONTH) < 9
                ? "0" +localDate.get(DAY_OF_MONTH) : ""+localDate.get(DAY_OF_MONTH);
        String month = localDate.get(MONTH_OF_YEAR) < 9
                ? "0" +localDate.get(MONTH_OF_YEAR) : ""+localDate.get(MONTH_OF_YEAR);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y");
        int year1 = Year.parse(year,formatter).getValue();
        String text = year1 +"-"+month+"-"+dayOfManth;

        LocalDate localDate1 = LocalDate.parse(text);
        DayOfWeek findDay = localDate1.getDayOfWeek();
        System.out.println(findDay);
        String day = findDay.getDisplayName(TextStyle.FULL, Locale.ITALIAN);

        return day;
    }
}


/*
        localDate = LocalDate.parse(result);

        int dayOfManth = localDate.get(DAY_OF_MONTH);
        int manth = localDate.get(MONTH_OF_YEAR);
        int year1 = (int) Integer.parseInt(year);

        LocalDate localDate1 = LocalDate.of(year1,manth,dayOfManth);

        DayOfWeek nextDay = localDate1.getDayOfWeek();
        String day = nextDay.getDisplayName(TextStyle.FULL, Locale.ITALIAN);

 */

/*
Buon Compleanno!
Реализуй метод getWeekdayOfBirthday.
Метод должен возвращать день недели на итальянском языке, в который будет (или был) день рождения в определенном году.
Пример формата дат смотри в методе main.

Пример:
1) Для "30.05.1984" и "2015" метод должен вернуть: sabato
2) Для "1.12.2015" и "2016" метод должен вернуть: gioved?

Выполни задание, используя Java 8 DateTime API.
Требования:
1. Используй статический метод parse класса LocalDate.
2. Используй статический метод parse класса Year.
3. Используй локаль Locale.ITALIAN.
4. Метод getWeekdayOfBirthday должен возвращать правильный день недели для передаваемых параметров.
 */