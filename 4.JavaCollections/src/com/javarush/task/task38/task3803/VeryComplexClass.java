package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.util.ArrayList;
import java.util.List;

public class VeryComplexClass {

    public void methodThrowsClassCastException() {

        List list = new ArrayList();
        list.add("a");
        list.add("b");

        System.out.println((int)list.get(0) - (int)list.get(1));

    }

    public void methodThrowsNullPointerException() {
        Object object = new Object();
        object = null;
        System.out.println(object.getClass());
    }

    public static void main(String[] args) {
        new VeryComplexClass().methodThrowsClassCastException();
        new VeryComplexClass().methodThrowsNullPointerException();

    }
}
