package com.javarush.task.task40.task4007;

/*
Работа с датами
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Solution {
    public static void main(String[] args) throws ParseException {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) throws ParseException {
        Calendar calendar =Calendar.getInstance( Locale.getDefault());
        String [] t = date.split("\\W");
        for (int i = 0; i < t.length; i++) {
//            System.out.println(t[i]);
        }
//День: 21
//День недели: 1
//День месяца: 21
//День года: 111
//Неделя месяца: 4
//Неделя года: 17
//Месяц: 4
//Год: 2014
        SimpleDateFormat sdf = null;
        if(date.contains(".") && date.contains(":")){
            sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            calendar.setTime(sdf.parse(date));
            System.out.println("День: "+calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("День недели: "+(calendar.get(Calendar.DAY_OF_WEEK) == 1 ?
                    7 : calendar.get(Calendar.DAY_OF_WEEK)-1));
            System.out.println("День месяца: "+calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("День года: "+calendar.get(Calendar.DAY_OF_YEAR));
            System.out.println("Неделя месяца: "+calendar.get(Calendar.WEEK_OF_MONTH));
            System.out.println("Неделя года: "+calendar.get(Calendar.WEEK_OF_YEAR));
            System.out.println("Месяц: "+(calendar.get(Calendar.MONTH) == 12 ? 1 : calendar.get(Calendar.MONTH)+1));
            System.out.println("Год: "+calendar.get(Calendar.YEAR));
            System.out.println();
            System.out.println("AM или PM: "+(calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM"));
            System.out.println("Часы: "+calendar.get(Calendar.HOUR));
            System.out.println("Часы дня: "+calendar.get(Calendar.HOUR_OF_DAY));
            System.out.println("Минуты: "+calendar.get(Calendar.MINUTE));
            System.out.println("Секунды: "+calendar.get(Calendar.SECOND));
        }
        if(date.contains(".") && !date.contains(":")){
            sdf = new SimpleDateFormat("dd.MM.yyyy");
            calendar.setTime(sdf.parse(date));
            System.out.println("День: "+calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("День недели: "+(calendar.get(Calendar.DAY_OF_WEEK) == 1 ?
                    7 : calendar.get(Calendar.DAY_OF_WEEK)-1));
            System.out.println("День месяца: "+calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("День года: "+calendar.get(Calendar.DAY_OF_YEAR));
            System.out.println("Неделя месяца: "+calendar.get(Calendar.WEEK_OF_MONTH));
            System.out.println("Неделя года: "+calendar.get(Calendar.WEEK_OF_YEAR));
            System.out.println("Месяц: "+(calendar.get(Calendar.MONTH) == 12 ? 1 : calendar.get(Calendar.MONTH)+1));
            System.out.println("Год: "+calendar.get(Calendar.YEAR));
        }
        if(!date.contains(".") && date.contains(":")){
            sdf = new SimpleDateFormat("HH:mm:ss");
            calendar.setTime(sdf.parse(date));
            System.out.println("AM или PM: "+(calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM"));
            System.out.println("Часы: "+calendar.get(Calendar.HOUR));
            System.out.println("Часы дня: "+calendar.get(Calendar.HOUR_OF_DAY));
            System.out.println("Минуты: "+calendar.get(Calendar.MINUTE));
            System.out.println("Секунды: "+calendar.get(Calendar.SECOND));
        }
    }
}
/*
Работа с датами
Реализуй метод printDate(String date).
Он должен в качестве параметра принимать дату (в одном из 3х форматов) и выводить ее в консоль в соответствии с примером:

1) Для "21.4.2014 15:56:45" вывод должен быть:
День: 21
День недели: 1
День месяца: 21
День года: 111
Неделя месяца: 4
Неделя года: 17
Месяц: 4
Год: 2014

AM или PM: PM
Часы: 3
Часы дня: 15
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

Используй класс Calendar.
Требования:
1. Если в метод printDate передана дата в формате "дата время",
он должен вывести: День, День недели, День месяца, День года,
Неделя месяца, Неделя года, Месяц, Год, AM или PM, Часы, Часы дня, Минуты, Секунды.

2. Если в метод printDate передана дата в формате "дата", он должен вывести:
День, День недели, День месяца, День года, Неделя месяца, Неделя года, Месяц, Год.

3. Если в метод printDate передана дата в формате "время", он должен вывести:
AM или PM, Часы, Часы дня, Минуты, Секунды.
4. Используй статический метод getInstance класса Calendar для получения календаря.
 */