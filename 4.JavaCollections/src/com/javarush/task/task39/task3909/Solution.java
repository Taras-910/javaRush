package com.javarush.task.task39.task3909;

/*
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("", "")); // true
        System.out.println(isOneEditAway("", "m")); //true
        System.out.println(isOneEditAway("m", "")); //true
//        System.out.println(isOneEditAway("m", null)); //
        System.out.println("------");
        System.out.println(isOneEditAway("mama", "ramas")); //false
        System.out.println(isOneEditAway("mamas", "rama")); //false
        System.out.println(isOneEditAway("rama", "mama")); //true
        System.out.println(isOneEditAway("mama", "dama")); //true
        System.out.println(isOneEditAway("amms", "amm"));  //false
        System.out.println(isOneEditAway("mama", "ama")); //true
    }

    public static boolean isOneEditAway(String first, String second) {


        if (first.equals("") && second.equals("")) return true;
        if (first.equals(second)) return true;

            StringBuffer large = first.length() >= second.length() ? new StringBuffer(first) : new StringBuffer(second);
            StringBuffer shot = first.length() < second.length() ? new StringBuffer(first) : new StringBuffer(second);

            int delta = large.length() - shot.length();

            if (delta > 1) return false;
            for (int i = 0; i < shot.length(); i++) {

                if (large.charAt(i) != shot.charAt(i)) {

                    if (delta != 0) {
                        large.deleteCharAt(i);
                    } else {
                        large.deleteCharAt(i);
                        shot.deleteCharAt(i);
                    }

                    break;
                }
            }

            if (large.length() != shot.length()) large.deleteCharAt(large.length() - 1);

            return large.toString().equals(shot.toString());

    }
}
/*
525
с 3-й попытки
1 - забыл добавить проверку на пустые строки
2 - добавил, при пустой строке выбрасывал false
3 - поменял на true, все приняло
Одно изменение
Реализуй метод isOneEditAway(String first, String second) который будет возвращать true,
если возможно изменить/добавить/удалить один символ в одной из строк и получить другую.

Символы в анализируемой строке ограничены кодировкой ASCII.
Регистр символов учитывается.

Требования:
1. Метод isOneEditAway должен корректно работать для строк одинаковой длины.
2. Метод isOneEditAway должен корректно работать для строк разной длины.
3. Метод isOneEditAway должен корректно работать для пустых строк.
4. Метод isOneEditAway должен быть публичным.
 */