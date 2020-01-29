package com.javarush.task.task38.task3804;

public class ExceptionFactory {

    public static Throwable getFactoryClass(Enum word) {
        if(word == null) return new IllegalArgumentException();
        String message  = word.toString().charAt(0) + word.toString().toLowerCase().substring(1).replace("_"," ");

        if(word instanceof ApplicationExceptionMessage)  return new Exception(message);
        else if(word instanceof DatabaseExceptionMessage)  return new RuntimeException(message);
        else if(word instanceof UserExceptionMessage)  return new Error(message);
        else return new IllegalArgumentException();
    }

}
//ApplicationExceptionMessage.
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