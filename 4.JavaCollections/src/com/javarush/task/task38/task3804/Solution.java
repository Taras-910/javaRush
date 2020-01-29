package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

public class Solution {
    public static Class getFactoryClass( ) {
        return ExceptionFactory.class;    }

    public static void main(String[] args) {
        ExceptionFactory exceptionFactory = new ExceptionFactory();
        System.out.println( exceptionFactory.getFactoryClass(ApplicationExceptionMessage.SOCKET_IS_CLOSED));
        System.out.println( exceptionFactory.getFactoryClass(ApplicationExceptionMessage.UNHANDLED_EXCEPTION));
        System.out.println( exceptionFactory.getFactoryClass(DatabaseExceptionMessage.NO_RESULT_DUE_TO_TIMEOUT));
        System.out.println( exceptionFactory.getFactoryClass(DatabaseExceptionMessage.NOT_ENOUGH_CONNECTIONS));
        System.out.println( exceptionFactory.getFactoryClass(UserExceptionMessage.USER_DOES_NOT_EXIST));
        System.out.println( exceptionFactory.getFactoryClass(UserExceptionMessage.USER_DOES_NOT_HAVE_PERMISSIONS));
        System.out.println(getFactoryClass());
    }
}

/*
Фабрика исключений
Создай класс - фабрику исключений, который содержит один статический метод, возвращающий нужное исключение.
Этот метод должен принимать один параметр - энум.
Если передан энум:
* ApplicationExceptionMessage, верни исключение Exception
* DatabaseExceptionMessage, верни исключение RuntimeException
* UserExceptionMessage, верни Error иначе верните IllegalArgumentException без сообщения.

В качестве сообщения (message) для каждого возвращаемого объекта используйте элементы энума,
в которых все знаки подчеркивания замените на пробелы. Сообщение должно быть в нижнем регистре за исключением первого символа.
Например, сообщение для ApplicationExceptionMessage.SOCKET_IS_CLOSED - "Socket is closed".

Верните класс созданной фабрики в методе Solution.getFactoryClass.

Энумы не меняй.

Требования:
1. Метод getFactoryClass должен возвращать фабрику исключений.
2. ApplicationExceptionMessage не должен содержать никакие дополнительные методов или конструкторы.
3. DatabaseExceptionMessage не должен содержать никакие дополнительные методов или конструкторы.
4. UserExceptionMessage не должен содержать никакие дополнительные методов или конструкторы.
5. Статический метод фабрики исключений должен возвращать исключения перечисленные в условии (включая сообщение) для любых входящих параметров.
6. Фабрика должна иметь один метод и он должен быть статическим.
 */