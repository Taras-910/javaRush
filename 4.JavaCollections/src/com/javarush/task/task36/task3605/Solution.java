package com.javarush.task.task36.task3605;
import java.io.*;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
            String strLine;
            String data;
            StringBuilder sb = new StringBuilder();
            TreeSet<Character> mySet = new TreeSet();

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/taras/Downloads/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task36/task3605/file1"))) {
//            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                while ((strLine = br.readLine()) != null) {
                    data = strLine.toLowerCase().replaceAll("[\\W\\d_]", "");
                    char[] line = data.toCharArray();
                    for (char s : line) {
                        mySet.add(s);
                    }
                }
            }
        mySet.stream().limit(5).forEach(System.out::print);

/*
        for (char s : mySet) {
            if(sb.length() < 5) sb.append(s);
        }
                System.out.printf(sb.toString());
*/
    }
}
/*


Первым параметром приходит имя файла: файл1.
файл1 содержит только буквы латинского алфавита, пробелы, знаки препинания, тире, символы перевода каретки.
Отсортируй буквы по алфавиту и выведи на экран первые 5 различных букв в одну строку без разделителей.
Если файл1 содержит менее 5 различных букв, то выведи их все.
Буквы различного регистра считаются одинаковыми.
Регистр выводимых букв не влияет на результат.
Закрой потоки.

Пример 1 данных входного файла:
zBk yaz b-kN

Пример 1 вывода:
abkny

Пример 2 данных входного файла:
caAC
A, aB? bB

Пример 2 вывода:
abc

Подсказка: использовать TreeSet


Требования:
1. Программа должна использовать класс TreeSet.
2. У объекта типа TreeSet вызови метод add для добавления элементов.
3. Программа должна выводить результат на экран.
4. Вывод программы должен соответствовать условию задачи.
 */

/*
        FileInputStream fis = new FileInputStream(args[0]);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String strLine;
        StringBuilder sb = new StringBuilder();
        while ((strLine = br.readLine()) != null)   {
            sb.append(strLine);
        }
        fis.close();
        String data = sb.toString();


        char[] line = data.toLowerCase().replaceAll("[^a-z^A-Z]","").toCharArray();
        TreeSet<Character> mySet = new TreeSet();
        for(char s : line){
            mySet.add(s);
        }



//   Iterator<Character> iterator = set.iterator();


        StringBuilder sb1 = new StringBuilder();
        for(char s : mySet){
            sb1.append(s);
        }
        System.out.printf("Пример %s данных входного файла:\n%s\nПример %s вывода:\n%s",numb,data,numb,sb1.toString().substring(0,5));

 */