package com.javarush.task.task35.task3508;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    String partOfName;
    String partOfContent = "interface Animal";
    List<Path> list = new ArrayList<>();
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        boolean containsName = true;

/*
        if (partOfName != null && !file.getFileName().toString().contains(partOfName))
            containsName = false;
*/

        String content = new String(Files.readAllBytes(file));
        boolean containsContent = true;
        if (!content.contains(partOfContent))
            containsContent = false;

        if (containsContent)
            list.add(file.getParent());
        return FileVisitResult.CONTINUE;
    }
}


