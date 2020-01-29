package com.javarush.task.task39.task3904;

import java.util.ArrayList;

/*
Лестница
*/
public class Solution {
    private static int n = 70;
    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        ArrayList<Long> list = new ArrayList<>();
        if(n < 0) return 0;
        list.add((long) 1);
        list.add((long) 1);
        list.add((long) 2);
        list.add((long) 4);
        if(n > 3){
            for(int i = 4; i <= n; i++){
                long s1 = list.get(i - 3);
                long s2 = list.get(i - 2);
                long s3 = list.get(i - 1);
                long s = s1 + s2 + s3;
                list.add(s);
            }
        }
        return list.get(n);
    }
}

/*
Для 70 ответ 2073693258389777176

Лестница
Ребенок бежит по лестнице состоящей из N ступенек, за 1 шаг он может пройти одну, две или три ступеньки.

Реализуй метод numberOfPossibleAscents(int n), который вернет количество способов которыми ребенок может пробежать
всю лестницу состоящую из n ступенек.

P.S. Если лестница состоит из 0 ступенек - метод должен возвращать 1. Для n < 0 верни 0.


Требования:
1. Метод numberOfPossibleAscents должен возвращать количество способов прохождения лестницы из n ступенек.
2. Метод numberOfPossibleAscents должен возвращать 1 для n = 0.
3. Метод numberOfPossibleAscents должен возвращать 0 для n < 0.
4. Время выполнения метода numberOfPossibleAscents должно быть линейным.
 */