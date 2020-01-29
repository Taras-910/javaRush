package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {

     public void testStorage(Shortener shortener){

        String s1 = Helper.generateRandomString();
        String s2 = Helper.generateRandomString();
        String s3 = s1;

        Long key1 = shortener.getId(s1);
        Long key2 = shortener.getId(s2);
        Long key3 = shortener.getId(s3);

        String str1 = shortener.getString(key1);
        String str2 = shortener.getString(key2);
        String str3 = shortener.getString(key3);

        Assert.assertNotEquals("key2 != key1", key2, key1);
        Assert.assertNotEquals("key2 != key3", key2, key3);
        Assert.assertEquals("key1 == key3", key1, key3);

        Assert.assertEquals("s1 == str1", s1, str1);
        Assert.assertEquals("s2 == str2", s2, str2);
        Assert.assertEquals("s3 == str3", s3, str3);
    }

    @Test
    public void testHashMapStorageStrategy(){
        HashMapStorageStrategy hashMapStorageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(hashMapStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy(){
        OurHashMapStorageStrategy ourHashMapStorageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashMapStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy(){
        FileStorageStrategy fileStorageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(fileStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy(){
        HashBiMapStorageStrategy hashBiMapStorageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(hashBiMapStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy(){
        DualHashBidiMapStorageStrategy dualHashBidiMapStorageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(dualHashBidiMapStorageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy(){
        OurHashBiMapStorageStrategy ourHashBiMapStorageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashBiMapStorageStrategy);
        testStorage(shortener);
    }
}
/*
Shortener (14)
Мы много раз тестировали наши стратегии с помощью метода testStrategy() класса Solution.
Пришло время написать настоящие юнит тесты с использованием junit.
14.1. Прочитай что такое юнит тесты.
14.2. Скачай и подключи библиотеку Junit 4.12. Разберись как ей пользоваться. Библиотека Junit зависит от библиотеки hamcrest-core.
Подключи и ее. Используй версию 1.3.
14.3. Добавь класс FunctionalTest в пакет tests. В этом классе мы проверим функциональность наших стратегий.
14.4. Добавь в класс FunctionalTest метод testStorage(Shortener shortener). Он должен:

14.4.1. Создавать три строки. Текст 1 и 3 строк должен быть одинаковым.
14.4.2. Получать и сохранять идентификаторы для всех трех строк с помощью shortener.
14.4.3. Проверять, что идентификатор для 2 строки не равен идентификатору для 1 и 3 строк.

Подсказка: метод Assert.assertNotEquals.

14.4.4. Проверять, что идентификаторы для 1 и 3 строк равны.

Подсказка: метод Assert.assertEquals.

14.4.5. Получать три строки по трем идентификаторам с помощью shortener.
14.4.6. Проверять, что строки, полученные в предыдущем пункте, эквивалентны оригинальным.

Подсказка: метод Assert.assertEquals.

14.5. Добавь в класс FunctionalTest тесты:
14.5.1. testHashMapStorageStrategy()
14.5.2. testOurHashMapStorageStrategy()
14.5.3. testFileStorageStrategy()
14.5.4. testHashBiMapStorageStrategy()
14.5.5. testDualHashBidiMapStorageStrategy()
14.5.6. testOurHashBiMapStorageStrategy()
Каждый тест должен иметь аннотацию @Test, создавать подходящую стратегию, создавать объект класса Shortener на базе этой стратегии
    и вызывать метод testStorage для него.
Запусти и проверь, что все тесты проходят.

Требования:
1. Класс FunctionalTest должен быть добавлен в созданный пакет tests.
2. В методе testStorage должны быть трижды вызваны методы getId и getString.
3. Тестовые методы перечисленные в условии задачи должны быть отмечены только аннотацией @Test.
4. В каждом тестовом методе должен содержаться вызов метода testStorage.

 */

/*
    @Test
    public void testHashMapStorageStrategy(){}
    @Test
    public void testOurHashMapStorageStrategy();
    @Test
    public void testFileStorageStrategy();
    @Test
    public void testHashBiMapStorageStrategy();
    @Test
    public void testDualHashBidiMapStorageStrategy();
    @Test
    public void testOurHashBiMapStorageStrategy();
*/