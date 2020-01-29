package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        
        FileReader fileReader = new FileReader("unknown.txt");    }

    public static void main(String[] args) throws Exception {
//        VeryComplexClass veryComplexClass = new VeryComplexClass();
//        veryComplexClass.veryComplexMethod();

        /*
        6
postgres
sqlite
oracle
mongodb
postgres
mssql
         */

        Map<String,Integer> map = new TreeMap();
        Scanner scan = new Scanner(System.in);
        String[] key = null;
            key = scan.nextLine().toLowerCase().split(" ");
            for(String s : key){
                if(map.containsKey(s)) map.put(s, map.get(s) + 1);
                else map.put(s, 1);
        }
        for(String s: map.keySet()) System.out.println(s+"="+map.get(s));


//
//        for(String s: map.keySet()) System.out.println(s+"="+map.get(s));


//String key





    }
}

/*
Проверяемые исключения (checked exception)
Напиши реализацию метода veryComplexMethod().
Он должен всегда кидать какое-нибудь проверяемое исключение.
Кинуть исключение (throw) явно нельзя.

Требования:
1. Метод veryComplexMethod класса veryComplexClass не должен использовать ключевое слово throw.
2. Метод veryComplexMethod класса veryComplexClass должен бросать исключение.
3. Брошенное исключение НЕ должно быть исключением времени выполнения(RuntimeException).
4. Метод veryComplexMethod не должен быть приватным.
 */