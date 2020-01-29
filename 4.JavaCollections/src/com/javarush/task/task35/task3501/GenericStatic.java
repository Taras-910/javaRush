package com.javarush.task.task35.task3501;

public class GenericStatic {


    public static <T> T someStaticMethod(T genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }

}



/*
    public static Object someStaticMethod(Object genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }


<? extends A> myMethod(List<? extends A> a)
 */