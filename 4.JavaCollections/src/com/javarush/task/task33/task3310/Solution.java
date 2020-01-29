package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Solution {
    public static void main(String[] args) {

        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        testStrategy(new FileStorageStrategy(), 100);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);

    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> setIds = new HashSet<>();
        for(String s : strings){
//            System.out.println("Solution 19:  method Set<Long> getId = "+ shortener.getId(s));   // не приходит
            setIds.add(shortener.getId(s));
        }
        return setIds;
    }
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> setStrings = new HashSet<>();
        for(Long k : keys){
            setStrings.add(shortener.getString(k));
        }
        return setStrings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber){

        System.out.println(strategy.getClass().getSimpleName());
        Set <String> stringsOfTest = new HashSet<>();
        for(long i = 0L; i < elementsNumber; i++) {
            stringsOfTest.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date curentTime = new Date();
//        System.out.println("solution 48:  stringsOfTest = "+stringsOfTest);
        Set keysOfTest = getIds(shortener,stringsOfTest);                        // ->
//        System.out.println("solution 49:  keysOfTest = "+keysOfTest+"\n\n");


//        System.out.println("solution 50:  keysOfTest = "+keysOfTest);
        Date newTime = new Date();
        Long msDelayGetId = newTime.getTime() - curentTime.getTime();
        System.out.println(msDelayGetId);

        Date curentTime2 = new Date();
//        System.out.print("Solution 46: keysOfTest = " +keysOfTest);
        Set stringsResultOfTest = getStrings(shortener,keysOfTest);
//        System.out.println(" solution 48:  stringsResultOfTest = "+stringsResultOfTest);

        Date newTime2 = new Date();
        Long msDelayGetString = newTime2.getTime() - curentTime2.getTime();
        System.out.println(msDelayGetString);

        if(stringsOfTest.containsAll(stringsResultOfTest)) System.out.println("Тест пройден.");
        else System.out.print("Тест не пройден.");
    }
}

/*
Junit 4.12. hamcrest-core 1.3.

Shortener (6)

6.2.3. testStrategy(StorageStrategy strategy, long elementsNumber).
       Метод будет тестировать работу переданной стратегии на определенном количестве элементов elementsNumber. Реализация метода должна:
6.2.3.1. Выводить имя класса стратегии. Имя не должно включать имя пакета.
6.2.3.2. Генерировать тестовое множество строк, используя Helper и заданное количество элементов elementsNumber.
6.2.3.3. Создавать объект типа Shortener, используя переданную стратегию.
6.2.3.4. Замерять и выводить время необходимое для отработки метода getIds для заданной стратегии и заданного множества элементов.
         Время вывести в миллисекундах. При замере времени работы метода можно пренебречь переключением процессора на другие потоки,
         временем, которое тратится на сам вызов, возврат значений и вызов методов получения времени (даты).
         Замер времени произведи с использованием объектов типа Date.

6.2.3.5. Замерять и выводить время необходимое для отработки метода getStrings для заданной стратегии и полученного
         в предыдущем пункте множества идентификаторов.

6.2.3.6. Сравнивать одинаковое ли содержимое множества строк, которое было сгенерировано и множества, которое было возвращено методом getStrings.
         Если множества одинаковы, то выведи "Тест пройден.", иначе "Тест не пройден.".
6.2.4. Добавь метод main(). Внутри метода протестируй стратегию HashMapStorageStrategy с помощью 10000 элементов.
6.3. Проверь, что программа работает и тест пройден.

Первая стратегия готова, пришло время ее протестить. Для этого:
6.1. Создай класс Solution, если ты не сделал это раньше.
6.2. Добавь в класс Solution реализации вспомогательных статических методов:
6.2.1. Set<Long> getIds(Shortener shortener, Set<String> strings).
       Этот метод должен для переданного множества строк возвращать множество идентификаторов.
       Идентификатор для каждой отдельной строки нужно получить, используя shortener.
6.2.2. Set<String> getStrings(Shortener shortener, Set<Long> keys).
       Метод будет возвращать множество строк, которое соответствует переданному множеству идентификаторов.
       При реальном использовании Shortener, задача получить из множества строк множество идентификаторов
       и наоборот скорее всего не встретится, это нужно исключительно для тестирования.

Требования:
1. Метод getIds должен для переданного множества строк возвращать множество идентификаторов.
2. Метод getStrings должен для переданного множества идентификаторов возвращать множество строк.
3. Метод testStrategy должен выводить на экран имя класса стратегии.
4. Метод testStrategy должен генерировать тестовое множество строк используя метод generateRandomString класса Helper
   (количество элементов должно быть равно параметру elementsNumber).
5. Метод testStrategy должен создавать объект типа Shortener используя переданную стратегию.
6. Метод testStrategy должен замерять количество времени выполнения методов getIds и getStrings используя объекты типа Date.
7. Метод testStrategy должен сравнивать количество сгенерированных строк и строк в множестве
   полученном в результате работы метода getStrings и выводить на экран результат прохождения теста.
8. В методе main должен быть вызван метод testStrategy.
 */







