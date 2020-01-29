package com.javarush.task.task37.task3707;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Solution {

    public static final Logger LOGGER = LoggerFactory.getLogger(Solution.class);
public static void main(String[] args) throws Exception {

        AmigoSet initialAmigoSet = new AmigoSet<>();

        for (int i = 0; i < 10; i++) {
        initialAmigoSet.add(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(initialAmigoSet);

        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        AmigoSet loadedAmigoSet = (AmigoSet) objectInputStream.readObject();

        System.out.println(initialAmigoSet.size() + " " + loadedAmigoSet.size());
        }
}


/*
public class Solution {

    public static final Logger LOGGER = LoggerFactory.getLogger(Solution.class);


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("ddd");
        hashSet.add("rrrr");
        AmigoSet amigoSet = new AmigoSet(hashSet);
        LOGGER.info("после созданиея объекта AmigoSet ="+ amigoSet.toString());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(amigoSet);
        objectOutputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        LOGGER.info("перед созданием объекта AmigoSet1");

        AmigoSet amigoSet1  = (AmigoSet) objectInputStream.readObject();

        objectInputStream.close();

        LOGGER.info("после созданиея объекта AmigoSet1 ="+ amigoSet1.toString());

        System.out.println(amigoSet.equals(amigoSet1));
        System.out.println("amigoSet  "+amigoSet);
        System.out.println("________");
        System.out.println("amigoSet1 "+amigoSet1);
    }
}*/
