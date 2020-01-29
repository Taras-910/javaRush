package com.javarush.task.task39.task3903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* 
Неравноценный обмен
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Please enter a number: ");

        long number = Long.parseLong(reader.readLine());
        System.out.println("Please enter the first index: ");
        int i = Integer.parseInt(reader.readLine());
        System.out.println("Please enter the second index: ");
        int j = Integer.parseInt(reader.readLine());

        System.out.println("The result of swapping bits is " + swapBits(number, i, j));
    }

    public static long swapBits(long number, int i, int j) {
        long result = 0;
        ArrayList<Integer> list = new ArrayList();
        int n = 0;

        while (n <= 64){
            if (number % 2 == 0) list.add(0);
            else list.add(1);
            number = number/2;
            n++;
        }

        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);

        for(int t = 0; t < list.size(); t++){
            result = result + list.get(t)*(long)Math.pow(2,t);
        }

        return result;
    }
}

/*
Проверь, что число 1 превращается в число 128
после обмена битов на позициях 0 и 7. 0000 .... 0000 0001 -> 0000 .... 1000 0000

Неравноценный обмен
Продолжая разработку алгоритма, нам очень бы пригодился метод который бы менял указанные биты
в двоичном представлении числа типа long.

Реализуй метод long swapBits(long number, int i, int j), который будет в двоичном представлении числа number
менять местами биты с индексами i и j и возвращать результат.

Наименее значимый бит имеет индекс 0.

Требования:
1. Метод swapBits должен быть реализован в соответствии с условием задачи.
2. Метод swapBits должен быть публичным.
3. Метод swapBits должен быть статическим.
4. Метод swapBits должен возвращать число типа long.
 */