package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class object) {

        if(object.isAnnotationPresent(PrepareMyTest.class)){
            PrepareMyTest prepareMyTest = (PrepareMyTest) object.getAnnotation(PrepareMyTest.class);
            String[] fullyQualifiedNames = prepareMyTest.fullyQualifiedNames();
            for(String s : fullyQualifiedNames) {
                System.out.println(s);
            }
            return true;
        } else return false;
    }

    public static boolean printValues(Class object) {

        if(object.isAnnotationPresent(PrepareMyTest.class)){
            PrepareMyTest prepareMyTest = (PrepareMyTest) object.getAnnotation(PrepareMyTest.class);
            Class<?>[] value = prepareMyTest.value();
            for(Class<?> s : value) {
                System.out.println(s.getSimpleName());
            }
            return true;
        } else return false;
    }
}

/*
Обработка аннотаций
В классе Solution необходимо реализовать простейшую обработку аннотаций.

В методы printFullyQualifiedNames() и printValues() приходит класс. Если этот класс отмечен аннотацией PrepareMyTest,
необходимо вывести на экран fullyQualifiedNames и values в соответствующих методах и вернуть true. Если же аннотация PrepareMyTest отсутствует - вернуть false.

Для вывода на экран классов из массива value используй сокращенное имя (getSimpleName).

Требования:
1. Метод printFullyQualifiedNames() должен возвращать false в случае, если переданный в качестве параметра класс не отмечен аннотацией @PrepareMyTest.
2. Метод printValues() должен возвращать false в случае, если переданный в качестве параметра класс не отмечен аннотацией @PrepareMyTest.
3. Метод printFullyQualifiedNames() должен выводить на экран все элементы массива fullyQualifiedNames, и возвращать true,
если полученный класс отмечен аннотацией @PrepareMyTest.
4. Метод printValues() должен выводить на экран все элементы массива value используя их сокращенное имя, и возвращать true,
если полученный класс отмечен аннотацией @PrepareMyTest.
 */