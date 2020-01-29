package com.javarush.task.task35.task3507;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
//Переопределяем метод findClass, eму надо передать путь к файлу с расширением .class
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File(name);
        System.out.println("\nпуть к файлу: "+name);
        if(!f.isFile()) throw new ClassNotFoundException("Нет такого класса " + name);
        InputStream ins = null;
        try{
            ins = new BufferedInputStream(new FileInputStream(f));
            byte[] buffer = Files.readAllBytes(Paths.get(name));
            Class c = defineClass(null, buffer, 0, buffer.length);
/*
            byte[]buffer = new byte[(int)f.length()];
            ins.read(buffer);
            Class c = (Class) defineClass(null, buffer, 0, buffer.length);
*/
            return c;                                          //возвращаем результат
        }catch (Exception e){
            e.printStackTrace();
            throw new ClassNotFoundException("Проблемы с байт кодом");
        }
        finally {
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}