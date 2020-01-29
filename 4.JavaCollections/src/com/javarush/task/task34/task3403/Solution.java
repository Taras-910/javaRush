package com.javarush.task.task34.task3403;

import java.util.concurrent.TimeUnit;

/*
Разложение на множители с помощью рекурсии
*/
public class Solution {

    public static void main(String[] args) {
        new Solution().recurse(132);
    }
    public void recurse(int n) {
        if (n <= 1) return;
        else {
            for (int a = 2; ; a++) {
                if (n % a == 0) {
                    n = n / a;
                    System.out.print(a + " ");
                    recurse(n);
                    break;
                }
            }
        }
    }
}