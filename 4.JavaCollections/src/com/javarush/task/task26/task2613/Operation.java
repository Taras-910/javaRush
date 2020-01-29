package com.javarush.task.task26.task2613;

public enum Operation {
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;
    public static Operation getAllowableOperationByOrdinal(Integer i) throws IllegalArgumentException {
        if(i < 1 || i > 4) throw new IllegalArgumentException();
        return Operation.values()[i];
    }
}
/*
CashMachine (6)
Чтобы отрефакторить код в соответствии с паттерном Command, нужно выделить в коде несколько логических блоков кода.
У нас пока два таких блока: 1) код операции DEPOSIT, 2) код операции INFO.
Они захардкожены в методе main. Нужно от этого избавиться.
Нужно сделать так, чтобы пользователь сам выбирал, какую операцию на данный момент нужно выполнять.

1. В энум Operation добавь статический метод Operation getAllowableOperationByOrdinal(Integer i)
Должен возвращать элемент энума: для 1 - INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT;
На некорректные данные бросать IllegalArgumentException.

2. В классе ConsoleHelper реализуй логику статического метода Operation askOperation().
Спросить у пользователя операцию.
Если пользователь вводит 1, то выбирается команда INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT;
Используйте метод, описанный в п.1.
Обработай исключение - запроси данные об операции повторно.

Требования:
1. Энум Operation должен иметь статический метод Operation getAllowableOperationByOrdinal(Integer i).
2. Класс ConsoleHelper должен иметь метод Operation askOperation().
 */