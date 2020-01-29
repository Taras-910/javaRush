package com.javarush.task.task36.task3602;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/*
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {

        Class<?>[] clazz = Collections.class.getDeclaredClasses();

        for(Class cl : clazz){

            if(!List.class.isAssignableFrom(cl)) continue;
            else{
                int classModifiers = cl.getModifiers();
                if(!Modifier.isPrivate(classModifiers) || !Modifier.isStatic(classModifiers)) continue;
                else{
                    try {
                        if(cl.getDeclaredConstructor().getParameterCount() == 0) return cl;
                    } catch (NoSuchMethodException e) { }
                }
            }
        }
        return null;
    }
}
/*


Найти класс по описанию
Описание класса:
1. Реализует интерфейс List;
2. Является приватным статическим классом внутри популярного утилитного класса;
3. Доступ по индексу запрещен - кидается исключение IndexOutOfBoundsException.
Используя рефлекшн (метод getDeclaredClasses), верни подходящий тип в методе getExpectedClass.

Требования:
1. Метод getExpectedClass должен использовать метод getDeclaredClasses подходящего утилитного класса.
2. Метод getExpectedClass должен вернуть правильный тип.
3. Метод main должен вызывать метод getExpectedClass.
4. Метод main должен вывести полученный класс на экран.
 */



/*
package com.javarush.task.task36.task3602;

import java.util.Collections;

public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        return null;
    }
}
 */