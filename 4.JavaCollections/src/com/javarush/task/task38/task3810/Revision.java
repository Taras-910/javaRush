package com.javarush.task.task38.task3810;

//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Target(value = ElementType.TYPE)
//@Retention(value= RetentionPolicy.RUNTIME)

public @interface Revision {
    //напиши свой код
    int revision();
    Date date();
    Author[] authors() default{};
    String comment() default "";
}

/*
cpublic @interface Version {
    int version();
    Date date();
    Author[] authors() default {};
    Class<?> previous() default Void.class;
}
 */