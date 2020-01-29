package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;
import static java.util.ResourceBundle.getBundle;

public class ConsoleHelper {
    private static ResourceBundle res = getBundle(RESOURCE_PATH +"common_en", Locale.getDefault());

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void printExitMessage(){
        System.out.println(res.getString("the.end"));
    }

    public static String readString() throws InterruptOperationException {
        String line = null;
        try {
            line = bis.readLine();
//            System.out.println(line.toUpperCase().matches("EXIT"));
            if(line.toUpperCase().matches("\\w*EXIT\\w*")) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {
            writeMessage("Ошибка ввода");
        }

        return line;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        boolean conditions = false;
        String currencyCode;
        while (!conditions) {
            writeMessage(res.getString("choose.currency.code"));

                currencyCode = readString();
            if (currencyCode != null && currencyCode.length() == 3 && !currencyCode.matches("\\d+")) {
                    conditions = true;
                    return currencyCode.toUpperCase();
                }
            else writeMessage(res.getString("invalid.data"));

        }
        return "ОШИБКА";
    }

    //line == null || !line.matches("\\d+\\s\\d+"))
    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        boolean conditions = false;
        String line = null;
        String[] line1 = null;

        while (!conditions) {
            writeMessage(res.getString("choose.denomination.and.count.format"));

                line = readString();

            if (line != null && line.matches("\\d+\\s\\d+")
                    && Integer.parseInt(line.split(" ")[0]) > 0
                    && Integer.parseInt(line.split(" ")[1]) > 0
            ) {
                conditions = true;
                return line.split(" ");
            }
            else writeMessage(res.getString("invalid.data"));
        }
        return null;
    }

    public static Operation askOperation() throws InterruptOperationException, IllegalArgumentException {
        boolean condition = false;
        Operation operation;
        while (!condition) {
            ConsoleHelper.writeMessage(res.getString("choose.operation")+ "\n" +
                    "1 - "+res.getString("operation.INFO")+", " +
                    "2 - "+res.getString("operation.DEPOSIT")+", " +
                    "3 - "+res.getString("operation.WITHDRAW")+", " +
                    "4 - "+res.getString("operation.EXIT")
            );
            String line = ConsoleHelper.readString();

            if(line != null && line.length() == 1 && line.matches("\\d")) {
                int i = Integer.parseInt(line);
                if(i == 0) throw new IllegalArgumentException();
                if(i < 0 || i > 4 ) continue;
                operation = Operation.getAllowableOperationByOrdinal(i);
                condition = true;
                return operation;
            }
        }
        return null;
    }
}
//     the.end=Terminated.Thank you for visiting JavaRush cash machine. Good luck.
//     choose.operation=Please choose an operation desired or type 'EXIT' for exiting
//    operation.INFO=INFO
//    operation.DEPOSIT=DEPOSIT
//    operation.WITHDRAW=WITHDRAW
//    operation.EXIT=EXIT
//invalid.data=Please specify valid data.
//      choose.currency.code=Please choose a currency code, for example USD
//      choose.denomination.and.count.format=Please specify integer denomination and integer count.
// For example '10 3' means 30 %s


/*
CashMachine (11)
Итак, назовем эту операцию LOGIN и сделаем для нее команду.
1. Добавь в операции LOGIN с ординал = 0
2. Запрети пользователю выбирать эту операцию из списка.
В единственном методе для поиска операций запрети доступ по ординал - бросим IllegalArgumentException.
3. Создай LoginCommand по аналогии с другими командами,
в котором захардкодь номер карточки с пином 123456789012 и 1234 соответственно.
4. Реализуй следующую логику для команды LoginCommand:
4.1. Пока пользователь не введет валидные номер карты и пин - выполнять следующие действия:
4.2. Запросить у пользователя 2 числа - номер кредитной карты, состоящий из 12 цифр, и пин - состоящий из 4 цифр.
4.3. Вывести юзеру сообщение о невалидных данных, если они такими являются.
4.4. Если данные валидны, то проверить их на соответствие захардкоженным (123456789012 и 1234).
4.5. Если данные в п. 4.4. идентифицированы, то сообщить, что верификация прошла успешно.
4.6. Если данные в п. 4.4. НЕ идентифицированы, то вернуться к п.4.1.
5. Исправь CommandExecutor. Добавь в allKnownCommandsMap новую операцию.
6. Исправь метод main.
Операция LOGIN должна запускаться один раз, до выполнения всех операций.
Не забудь о InterruptOperationException, в любом месте пользователь может завершить работу с банкоматом.
Поэтому добавь вызов операции внутрь блока try-catch.
Требования:
1. Энум Operation должен содержать операцию LOGIN с ординал = 0.
2. В методе getAllowableOperationByOrdinal(Integer i) запрети пользователю выбирать операцию LOGIN.
3. Метод execute класса LoginCommand должен реализовывать алгоритм для команды LOGIN, согласно заданию.
4. Карта allKnownCommandsMap класса CommandExecutor должна содержать новую операцию.
5. Метод main должен вызывать операцию LOGIN.

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

CashMachine (4)
1. Выберем операцию, с которой мы сможем начать.
Подумаем. В банкомате еще денег нет, поэтому INFO и WITHDRAW протестить не получится.
Начнем с операции DEPOSIT - поместить деньги.
Считаем с консоли код валюты, потом считаем номинал и количество банкнот, а потом добавим их в манипулятор.

2. Чтобы считать код валюты, добавим статический метод String askCurrencyCode() в ConsoleHelper.
Этот метод должен предлагать пользователю ввести код валюты, проверять, что код содержит 3 символа.
Если данные некорректны, то сообщить об этом пользователю и повторить.
Если данные валидны, то перевести код в верхний регистр и вернуть.

3. Чтобы считать номинал и количество банкнот, добавим статический метод
String[] getValidTwoDigits(String currencyCode) в ConsoleHelper.
Этот метод должен предлагать пользователю ввести два целых положительных числа.
Первое число - номинал, второе - количество банкнот.
Никаких валидаторов на номинал нет. Т.е. 1200 - это нормальный номинал.
Если данные некорректны, то сообщить об этом пользователю и повторить.
Пример вводимых данных:
200 5
4. В классе CurrencyManipulator создай метод void addAmount(int denomination, int count),
который добавит введенные номинал и количество банкнот.
5. Пора уже увидеть приложение в действии.
В методе main захардкодь логику пункта 1.
Кстати, чтобы не было проблем с тестами на стороне сервера, добавь в метод main первой строчкой
Locale.setDefault(Locale.ENGLISH);
Запускаем, дебажим, смотрим.
Требования:
1. Класс ConsoleHelper должен иметь статический метод String askCurrencyCode().
2. Класс ConsoleHelper должен иметь статический метод String[] getValidTwoDigits(String currencyCode).
3. Класс CurrencyManipulator должен иметь метод void addAmount(int denomination, int count).
4. Метод main класса CashMachine должен считывать с консоли код валюты, потом считывать номинал и количество банкнот,
а потом добавлять их в манипулятор.



1. Создай в ConsoleHelper два статических метода:
1.1 writeMessage(String message), который будет писать в консоль наше сообщение.
1.2 String readString(), который будет считывать с консоли строку и возвращать ее.
Если возникнет какое-то исключение при работе с консолью, то перехватим его и не будем обрабатывать.
Кстати, создадим только один экземпляр BufferedReader-а, в статическом поле bis.
2. Создай пакет exception, в который поместим два checked исключения:
2.1 InterruptOperationException будем кидать, когда нужно прервать текущую операцию и выйти из приложения.
2.2 NotEnoughMoneyException будем кидать, когда не сможем выдать запрашиваемую сумму.
Требования:
1. Класс InterruptOperationException должен быть создан в отдельном файле, и быть checked исключением.
2. Класс NotEnoughMoneyException должен быть создан в отдельном файле, и быть checked исключением.
3. Класс ConsoleHelper должен содержать приватное статическое поле BufferedReader bis,
которое должно быть сразу проинициализировано.
4. Класс ConsoleHelper должен содержать публичный статический метод writeMessage(String message),
который должен выводить в консоль переданный параметр.
5. Класс ConsoleHelper должен содержать публичный статический метод readString(),
который должен считывать с консоли строку и возвращать ее.
 */