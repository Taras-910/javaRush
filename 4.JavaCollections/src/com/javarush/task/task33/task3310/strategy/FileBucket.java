package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    Path path;
    public FileBucket() {
        try {
            this.path = Files.createTempFile(null,null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e1) { }

        path.toFile().deleteOnExit();
    }
    public long getFileSize(){     // он должен возвращать размер файла на который указывает path
        Long size = 0L;
        try {
            size = Files.size(path);
        } catch (IOException e) {}
        return size;
    }

    public void putEntry(Entry entry) {  // должен сериализовывать переданный entry в файл. Учти, каждый entry может содержать еще один entry

        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
            objectOutputStream.writeObject(entry);
            objectOutputStream.close();
        } catch (IOException e) {}
    }

    public Entry getEntry() {  // должен забирать entry из файла. Если файл имеет нулевой размер, вернуть null

        if(getFileSize() > 0) {
            Entry entry = null;
            ObjectInputStream objectInputStream;
            try {
                objectInputStream = new ObjectInputStream(Files.newInputStream(path));
                entry = (Entry) objectInputStream.readObject();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
            }
            return entry;
        }else return null;
    }

    public void remove() {  // удалять файл на который указывает path
        try {
            Files.delete(path);
        } catch (IOException e) {}
    }
}

/*
Shortener (9)
Напишем еще одну стратегию, назовем ее FileStorageStrategy. Она будет очень похожа на стратегию OurHashMapStorageStrategy,
но в качестве ведер (англ. buckets) будут файлы. Я знаю, ты знаешь о каких buckets идет речь, если нет - повтори внутреннее устройство HashMap.
9.1. Создай класс FileBucket в пакете strategy.
9.2. Добавь в класс поле Path path. Это будет путь к файлу.
9.3. Добавь в класс конструктор без параметров, он должен:
9.3.1. Инициализировать path временным файлом. Файл должен быть размещен в директории для временных файлов и иметь случайное имя.

Подсказка: Files.createTempFile.

9.3.2. Создавать новый файл, используя path. Если такой файл уже есть, то заменять его.
9.3.3. Обеспечивать удаление файла при выходе из программы.

Подсказка: deleteOnExit().

9.4. Добавь в класс методы:
9.4.1. long getFileSize(), он должен возвращать размер файла на который указывает path.
9.4.2. void putEntry(Entry entry) - должен сериализовывать переданный entry в файл. Учти, каждый entry может содержать еще один entry.
9.4.3. Entry getEntry() - должен забирать entry из файла. Если файл имеет нулевой размер, вернуть null.
9.4.4. void remove() - удалять файл на который указывает path.
Конструктор и методы не должны кидать исключения.


Требования:
1. В классе FileBucket должно быть создано поле path типа Path.
2. Конструктор без параметров класса FileBucket должен быть реализован в соответствии с условием задачи.
3. Метод getFileSize должен возвращать размер файла на который указывает path.
4. Метод putEntry должен сериализовывать полученный объект типа Entry в файл на который указывает path,
    чтобы получить OutputStream используй метод Files.newOutputStream.
5. Метод getEntry должен десериализовывать объект типа Entry из файл на который указывает path,
    чтобы получить InputStream используй метод Files.newInputStream.
6. Метод remove должен удалять файл на который указывает path с помощью метода Files.delete().
 */
