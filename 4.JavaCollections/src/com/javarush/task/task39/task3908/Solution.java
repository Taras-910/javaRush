package com.javarush.task.task39.task3908;

import java.util.TreeMap;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isPalindromePermutation("akdkaFfddDleL;'  ss"));
    }

    public static boolean isPalindromePermutation(String s) {
        int n = s.length();
        if(n != 0) {
            TreeMap map = new TreeMap();
            s = s.toLowerCase().replaceAll("\\W", "");
            for (int i = 0; i < s.length(); i++) {
                char a = s.charAt(i);
                if (!map.containsKey(a)) map.put(a, 1);
                else map.remove(a);
            }
            if (n % 2 == 0 && map.size() == 0) return true;
            if (n % 2 != 0 && map.size() == 1) return true;
        }
        return false;
    }
}

/*
Возможен ли палиндром?
Реализуй метод isPalindromePermutation(String s) который будет возвращать true,
если из всех символов строки s можно составить палиндром. Иначе - false.

Символы в анализируемой строке ограничены кодировкой ASCII.
Регистр букв не учитывается.

Требования:
1. Метод isPalindromePermutation должен возвращать true,
если выполнив перестановку символов входящей строки можно получить палиндром.
2. Метод isPalindromePermutation должен возвращать false,
если выполнив перестановку символов входящей строки получить палиндром невозможно.
3. Метод isPalindromePermutation должен быть публичным.
4. Метод isPalindromePermutation должен быть статическим.
 */