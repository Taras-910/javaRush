package com.javarush.task.task36.task3606;

import java.io.*;
import java.nio.file.Files;

public class MyClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) {
            File file = new File(name);
            InputStreamReader isr;
            Class clazz = null;
            try {
                isr = new InputStreamReader(new FileInputStream(file));
                byte [] bytes = Files.readAllBytes(file.toPath());
                clazz = defineClass(null,bytes,0,bytes.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return clazz;
        }
}
