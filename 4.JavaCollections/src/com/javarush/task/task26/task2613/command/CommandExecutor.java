package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.Operation;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

import static com.javarush.task.task26.task2613.Operation.*;

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

    static{
        allKnownCommandsMap.put(LOGIN, new LoginCommand());
        allKnownCommandsMap.put(INFO, new InfoCommand());
        allKnownCommandsMap.put(DEPOSIT, new DepositCommand());
        allKnownCommandsMap.put(WITHDRAW, new WithdrawCommand());
        allKnownCommandsMap.put(EXIT, new ExitCommand());
    }
    private CommandExecutor() {
    }

    public static final void execute(Operation operation) throws InterruptOperationException {

            allKnownCommandsMap.get(operation).execute();

    }
}
/*
CashMachine (7)
Возвращаемся к паттерну Command.
1. Создай пакет command, в нем будут все классы, относящиеся к этой логике.
Подумай над модификатором доступа для всех классов в этом пакете.
2. Создай интерфейс Command с методом void execute().
3. Для каждой операции создай класс-команду, удовлетворяющую паттерну Command.
Имена классов DepositCommand, InfoCommand, WithdrawCommand, ExitCommand.
4. Создай public класс CommandExecutor, через который можно будет взаимодействовать со всеми командами.
Создай ему статическую карту Map<Operation, Command> allKnownCommandsMap,
которую проинициализируй всеми известными нам операциями и командами.
4.1 Создай метод public static final void execute(Operation operation),
который будет дергать метод execute у нужной команды.
Реализуй эту логику.
4.2. Расставь правильно модификаторы доступа учитывая, что единственная точка входа - это метод execute.
Проверяем, чтоб структура соответствовала тестам на сервере.
Логику будем переносить в следующем таске.
Требования:
1. Интерфейс Command должен быть создан в отдельном файле.
2. В интерфейсе Command должен быть метод void execute().
3. В отдельном файле должен быть создан класс DepositCommand, реализующий интерфейс Command.
4. В отдельном файле должен быть создан класс InfoCommand, реализующий интерфейс Command.
5. В отдельном файле должен быть создан класс WithdrawCommand, реализующий интерфейс Command.
6. В отдельном файле должен быть создан класс ExitCommand, реализующий интерфейс Command.
7. В отдельном файле должен быть создан класс CommandExecutor.
8. Класс CommandExecutor должен содержать приватное статическое поле Map<Operation, Command> allKnownCommandsMap.
9. Класс CommandExecutor должен содержать public static final void метод execute(Operation operation).
10. Класс CommandExecutor должен содержать приватный конструктор.
11. Поле Map<Operation, Command> allKnownCommandsMap класса CommandExecutor должно быть проинициализировано
всеми известными нам операциями и командами.
 */