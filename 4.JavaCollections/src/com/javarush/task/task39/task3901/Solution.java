package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if(s == null || s.length() == 0) return 0;
        if(s.length() == 1) return 1;

        int numberMax = 1;
        int currentPoint = 1;
        int number =1;

        while(true){

            String currentString = String.valueOf(s.charAt(currentPoint));
            String word = s.substring(0, currentPoint);
            if(word.contains(currentString)){
                number = 1;
                s = s.substring(s.indexOf(currentString) +1);
                if(s.length() > 1) {
                    currentPoint = 1;
                }
                else return numberMax;
            }
            else{
                number++;
                numberMax = numberMax > number ? numberMax : number;
               if(currentPoint < s.length()-1) {
                   currentPoint++;
               }
               else return numberMax;
            }

        }
    }
}

/*
Уникальные подстроки
Реализуй метод lengthOfLongestUniqueSubstring таким образом,
чтобы он возвращал длину самой длинной подстроки без повторяющихся символов,
найденной в строке полученной в качестве параметра.
Например, для строки "a123bcbcqwe" - 6, а для строки "ttttwt" - 2.
Если анализируемая строка пуста или равна null - верни 0.


Требования:
1. Метод lengthOfLongestUniqueSubstring должен возвращать длину подстроки
с максимальным количеством уникальных символов.
2. Метод lengthOfLongestUniqueSubstring должен возвращать 0 для пустой строки, или строки равной null.
3. Метод lengthOfLongestUniqueSubstring должен быть публичным.
4. Метод lengthOfLongestUniqueSubstring должен быть статическим.
 */