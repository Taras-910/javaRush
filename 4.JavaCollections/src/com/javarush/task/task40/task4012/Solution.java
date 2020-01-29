package com.javarush.task.task40.task4012;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/* 
Полезные методы DateTime API
*/

public class Solution {
    public static void main(String[] args) {

        System.out.println(isLeap(LocalDate.parse("1/1/1976", DateTimeFormatter.ofPattern("d/M/yyyy"))));

        System.out.println(isBefore(LocalDateTime.parse("2015-02-20T06:30:00")));

        System.out.println(addTime(LocalTime.of(06,30,00,00),2,ChronoUnit.HOURS));

        System.out.println(getPeriodBetween(LocalDate.of(1980,1,10),
                LocalDate.parse("2014-02-25")
                ).getDays());

    }

    public static boolean isLeap(LocalDate date) {
        return date.isLeapYear();
    }

    public static boolean isBefore(LocalDateTime dateTime) {
        LocalDateTime date = LocalDateTime.now();
        return dateTime.isBefore(date);
    }

    public static LocalTime addTime(LocalTime time, int n, ChronoUnit chronoUnit) {

        return time.plus(n,chronoUnit);
    }

    public static Period getPeriodBetween(LocalDate firstDate, LocalDate secondDate) {

        LocalDate first = firstDate.isBefore(secondDate) ? firstDate : secondDate;
        LocalDate second = firstDate.isBefore(secondDate) ? secondDate : firstDate;

        return Period.between(first, second);
    }
}
/*
Полезные методы DateTime API
В Java 8 DateTime API реализовано множество классов и методов, которые существенно упрощают работу со временем и датами.
Реализуем несколько простых методов, чтобы познакомиться с ними поближе.
1) Метод isLeap должен принимать дату и возвращать true, если год является високосным, иначе - false.
2) Метод isBefore должен принимать дату и возвращать true, если она предшествует текущей дате, иначе - false.
3) Метод addTime должен возвращать полученное в качестве параметра время, увеличенное на n СhronoUnit.
4) Метод getPeriodBetween должен принимать две даты и возвращать временной промежуток между ними.
Помни, что в метод Period.between необходимо передать сначала меньшую, а затем большую дату.
Требования:
1. Метод isLeap должен принимать дату и возвращать true, если год является високосным, иначе - false.
2. Метод isBefore должен принимать дату и возвращать true, если она предшествует текущей дате, иначе - false.
3. Метод addTime должен возвращать полученное в качестве параметра время, увеличенное на n СhronoUnit.
4. Метод getPeriodBetween должен принимать две даты и возвращать временной промежуток между ними.
 */