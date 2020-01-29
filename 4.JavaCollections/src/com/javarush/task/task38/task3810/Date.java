package com.javarush.task.task38.task3810;

//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Target(value = ElementType.TYPE)
//@Retention(value= RetentionPolicy.RUNTIME)

public @interface Date {
    //напиши свой код
    int year();
    int month();
    int day();
    int hour();
    int minute();
    int second();
}
//
/*
public @interface Name
{
    String name();
    String type() default "string";
}
 */