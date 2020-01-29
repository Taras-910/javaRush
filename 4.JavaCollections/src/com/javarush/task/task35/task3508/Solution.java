package com.javarush.task.task35.task3508;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Solution {

	   public static void main(String[] args) throws IOException {

           //создание файла
/*
           Path testFile1 = Files.createFile(Paths.get("/Users/taras/Desktop/testFile111.txt"));
           System.out.print("Был ли файл успешно создан?  ");
           System.out.println(Files.exists(Paths.get("/Users/taras/Desktop/testFile111.txt")));

           //создание директории
           Path testDirectory2 = Files.createDirectory(Paths.get("/Users/taras/Desktop/testDirectory2"));
           System.out.print("Была ли директория успешно создана?  ");
           System.out.println(Files.exists(Paths.get("/Users/taras/Desktop/testDirectory2")));


           //копируем файл с рабочего стола в директорию testDirectory2.
           testFile1 = Files.copy(testFile1, Paths.get("/Users/taras/Desktop/testDirectory2/testFile111.txt"), REPLACE_EXISTING);
           System.out.print("Остался ли наш файл на рабочем столе?  ");
           System.out.println(Files.exists(Paths.get("/Users/taras/Desktop/testFile111.txt")));
           System.out.println("Был ли наш файл скопирован в testDirectory?  ");
           System.out.println(Files.exists(Paths.get("/Users/taras/Desktop/testDirectory2/testFile111.txt")));
*/
/*
           List<String> lines = Files.readAllLines(Paths.get("/Users/taras/Desktop/testDirectory2/testFile111.txt"), UTF_8);
           for (String s: lines) {
               System.out.println(s);
           }
*/
/*
           List<String> lines = Files.readAllLines(Paths.get("/Users/taras/Desktop/testDirectory2/testFile111.txt"), UTF_8);
           List<String> result = new ArrayList<>();
           for (String s: lines) {
               if (s.startsWith("Как")||s.startsWith("Я")) s = s.toUpperCase();
                   result.add(s);
           }
           for (String s: result) {
               System.out.println(s);
           }
*/
           Path file = Paths.get("/Users/taras/Downloads/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task35/task3507/data");
           MyFileVisitor myFileVisitor = new MyFileVisitor();
           Files.walkFileTree(file, myFileVisitor);
           System.out.println(myFileVisitor.list.toString());
       }
}





/*import java.util.List;

public abstract class Solution {
    public abstract <T> void one(List <T>destination, List<T>source);

    public abstract <T> void two(List <T>destination, List<? extends T>source);

    public abstract <T> void three(List<? super T> destination, List<T> source);

    public abstract <T> void four(List<? super T> destination, List<? extends T> source);
}*/
