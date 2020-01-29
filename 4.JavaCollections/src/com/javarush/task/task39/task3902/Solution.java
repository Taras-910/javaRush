package com.javarush.task.task39.task3902;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Биты были биты
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter a number: ");

        long l = Long.parseLong(reader.readLine());
        String result = isWeightEven(l) ? "even" : "odd";
        System.out.println("The entered number has " + result + "ones");

    }

    public static boolean isWeightEven(long number) {
        if(number == 0) return true;
        boolean result = false;
        while (number > 1){
            if (number % 2 != 0) result = !result;
            number = number/2;
        }

        return result;
    }
}

/*

Биты были биты
В процессе разработки сложного алгоритма кодирования возникла задача определить
четное ли количество единиц в двоичной записи числа.

Реализуй метод boolean isWeightEven(long number), который будет возвращать true или false в зависимости от того,
является ли количество единиц в двоичном представлении числа number четным или нечетным.

P.S. Постарайся использовать только побитовые операции, а также минимизировать время выполнения программы.


Требования:
1. Метод isWeightEven должен возвращать true, если количество единиц в двоичном представлении анализируемого числа четное.
2. Метод isWeightEven должен возвращать false, если количество единиц в двоичном представлении анализируемого числа нечетное.
3. Время выполнения метода isWeightEven должно быть максимально близко к оптимальному.
4. Метод isWeightEven должен быть публичным и статическим.
 */