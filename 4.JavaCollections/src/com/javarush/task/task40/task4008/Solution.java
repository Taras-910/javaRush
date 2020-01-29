package com.javarush.task.task40.task4008;

/*
Работа с Java 8 DateTime API
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static java.time.temporal.ChronoField.*;

public class Solution {
    public static void main(String[] args) throws ParseException {
        printDate("9.10.2017 5:56:45");
        System.out.println();
        printDate("21.4.2014");
//        printDate("31.12.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) throws ParseException {

        String [] d = date.split(" ");
        if(d.length == 2 && d[1].length() == 7) d[1] = "0" + d[1];
        if(d.length == 1 && date.length() == 7) date = "0" + date;


        LocalDate localDate = null;
        LocalTime localTime = null;

        if(date.contains(".")){
            String old = d.length == 2 ? d[0] : date;

            SimpleDateFormat oldFormat = new SimpleDateFormat("dd.mm.yyyy");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
            String result = newFormat.format(oldFormat.parse(old));



            localDate = LocalDate.parse(result);

            System.out.println("День: "+localDate.get(DAY_OF_MONTH));
            System.out.println("День недели: "+localDate.get(DAY_OF_WEEK));
            System.out.println("День месяца: "+localDate.get(DAY_OF_MONTH));
            System.out.println("День года: "+localDate.get(DAY_OF_YEAR));
            System.out.println("Неделя месяца: "+localDate.get(ALIGNED_WEEK_OF_MONTH));
            System.out.println("Неделя года: "+localDate.get(ALIGNED_WEEK_OF_YEAR));
            System.out.println("Месяц: "+ localDate.get(MONTH_OF_YEAR));
            System.out.println("Год: "+localDate.get(YEAR));
            System.out.println();
        }

        if(date.contains(":")){
            if(d.length == 2 ) date = d[1];

            localTime = LocalTime.parse(date);

            System.out.println("AM или PM: " + (localTime.get(AMPM_OF_DAY) == 0 ? "AM" : "PM"));
            System.out.println("Часы: " + localTime.get(HOUR_OF_AMPM));
            System.out.println("Часы дня: " + localTime.get(HOUR_OF_DAY));
            System.out.println("Минуты: " + localTime.get(MINUTE_OF_HOUR));
            System.out.println("Секунды: " + localTime.get(SECOND_OF_MINUTE));
        }
    }
}

/*
1) Для "9.10.2017 5:56:45" вывод должен быть:
День: 9
День недели: 1
День месяца: 9
День года: 282
Неделя месяца: 3
Неделя года: 42
Месяц: 10
Год: 2017
AM или PM: PM
Часы: 5
Часы дня: 5
Минуты: 56
Секунды: 45

2) Для "21.4.2014":
День: 21
День недели: 1
День месяца: 21
День года: 111
Неделя месяца: 4
Неделя года: 17
Месяц: 4
Год: 2014

3) Для "17:33:40":
AM или PM: PM
Часы: 5
Часы дня: 17
Минуты: 33
Секунды: 40


Требования:
1. Если в метод printDate передана дата в формате "дата время", он должен вывести:
День, День недели, День месяца, День года, Неделя месяца, Неделя года, Месяц, Год,
AM или PM, Часы, Часы дня, Минуты, Секунды.
2. Если в метод printDate передана дата в формате "дата", он должен вывести:
День, День недели, День месяца, День года, Неделя месяца, Неделя года, Месяц, Год.
3. Если в метод printDate передана дата в формате "время", он должен вывести:
AM или PM, Часы, Часы дня, Минуты, Секунды.
4. Используй статический метод parse классов LocalDate и LocalTime.
 */