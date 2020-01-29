package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

import static com.javarush.task.task26.task2613.Operation.EXIT;
import static com.javarush.task.task26.task2613.Operation.LOGIN;

public class CashMachine {
    public static final String RESOURCE_PATH  = CashMachine.class.getPackage().getName() + ".resources.";

    public static void main(String[] args)  {
        Locale.setDefault(Locale.ENGLISH);

        Operation operation;
        boolean condition = false;

        try {
            CommandExecutor.execute(LOGIN);
            do {
                operation = ConsoleHelper.askOperation();   // запрос у юзера типа операции
                CommandExecutor.execute(operation);
                if (operation == EXIT) condition = true;

            } while (!condition);
            } catch (InterruptOperationException | IllegalArgumentException e) {
                ConsoleHelper.printExitMessage();
                condition = true;
            }
    }
}
/*
CashMachine (15)
1. В CashMachine создай константу - путь к ресурсам.
public static final String RESOURCE_PATH;
Отрефакторь загрузку всех ResourceBundle с учетом RESOURCE_PATH.

2. В классе CashMachine не должно быть инициализации ResourceBundle.
Вынеси из CashMachine сообщение о выходе в ConsoleHelper, назови метод printExitMessage.
3. Это всё! Красоту можешь наводить самостоятельно. Тестов на этот пункт не будет.

Например:
3.1. Исправить выводимые тексты.
3.2. Добавить ресурсы для нескольких локалей. Например, еще и для русской.
3.3. Добавить валидацию вводимых номиналов.
Твои достижения:
1. разобрался с паттерном Command.
2. подружился с Жадным алгоритмом.
3. познакомился с локализацией.
4. стал больше знать и уметь.
5. увидел, как раскладывать задачу на подзадачи.
6. продвинулся на шаг ближе к работе джава программистом.
7. решил одно из тестовых заданий, которое дают на собеседовании. Только тсссс, никому об этом не говори :).
Если когда-то тебе дадут такое задание, то не копируй это решение, а сделай свое по аналогии.
Поздравляю!
Требования:
1. Класс CashMachine должен содержать public static final поле RESOURCE_PATH типа String.
2. Класс ConsoleHelper должен содержать public static void метод printExitMessage().
3. Поздравляю, это все на этот уровень!



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

if(number == null) {
                    do {
                        loginAdnPin = CommandExecutor.execute(LOGIN);

                        ConsoleHelper.writeMessage("Enter card number.");
                        number = ConsoleHelper.readString();

                        ConsoleHelper.writeMessage("Enter pin.");
                        pin = ConsoleHelper.readString();

                        if(number.matches("\\d{12}") && pin.matches("\\d{4}")) {

                            if (number.equals(loginAdnPin[0]) && pin.equals(loginAdnPin[1])) {
                                ConsoleHelper.writeMessage("Login was successful.");
                                condition1 = true;
                            }
                        } else ConsoleHelper.writeMessage("Wrong data.");

                    } while (!condition1);

                }

CashMachine (8)
Пора привести в порядок наш main, уж очень там всего много, чего не должно быть.
1. Перенеси логику из main в DepositCommand и InfoCommand.
Проверим, что там стало с main? Цикл, в котором спрашиваем операцию у пользователя,
а потом вызываем метод у CommandExecutor.
И так до бесконечности... надо бы придумать условие выхода из цикла.
Исправь цикл, чтоб он стал do-while. Условие выхода - операция EXIT.
2. Давай запустим прогу и пополним счет на EUR 100 2 и USD 20 6, и посмотрим на INFO.
Ничего не понятно, т.к. создались 2 манипулятора: первый для EUR, второй для USD.
Давай улучшим логику InfoCommand. Надо вывести баланс по каждому манипулятору.
2.1. В классе CurrencyManipulatorFactory создай статический метод getAllCurrencyManipulators(),
который вернет Collection всех манипуляторов.
У тебя все манипуляторы хранятся в карте, не так ли? Если нет, то отрефактори.
2.2. В InfoCommand в цикле выведите [код валюты - общая сумма денег для выбранной валюты].
Запустим прогу и пополним счет на EUR 100 2 и USD 20 6, и посмотрим на INFO.
Все работает правильно?
EUR - 200
USD - 120
Отлично!
3. Запустим прогу и сразу первой операцией попросим INFO. Ничего не вывело? Непорядок.
Добавь в манипулятор метод boolean hasMoney(), который будет показывать, добавлены ли какие-то банкноты или нет.
4. В InfoCommand используй метод п.3. и выведите фразу "No money available.", если нет денег в банкомате.
Требования:
1. В классе CurrencyManipulatorFactory должен быть публичный статический метод
Collection <CurrencyManipulator> getAllCurrencyManipulators().
2. В классе CurrencyManipulator должен быть метод boolean hasMoney().
3. В классе InfoCommand в методе execute() для каждого манипулятора выведи:
"код валюты - общая сумма денег для выбранной валюты", если денег нет в банкомате выведи фразу, "No money available.".
4. В классе DepositCommand в методе execute() запроси код валюты, потом запроси номинал и количество банкнот,
а потом добавь их в манипулятор. Если номинал и количество банкнот пользователь ввел не правильно(не числа) -
повторять попытку по введению номинала и количества банкнот.
5. В методе main класса CashMachine запроси операцию у пользователя.
Выполни операцию в CommandExecutor. Повторять пока пользователь не выбрал операцию EXIT.


CashMachine (5)
1.В предыдущем таске мы реализовали основную логику операции DEPOSIT.
Но посмотреть результат так и не удалось.
Поэтому создай в манипуляторе метод int getTotalAmount(), который посчитает общую сумму денег для выбранной валюты.
2. Добавь вызов метода getTotalAmount() в метод main.
Всё работает верно? Тогда движемся дальше.
Видно, что метод getTotalAmount() считает то, что нам необходимо для операции INFO.
Поэтому пришло время небольшого рефакторинга.
!!Читайте паттерн Command.
Однако, перед рефакторингом нужно еще разобраться в одном вопросе. Но об этом не сейчас.
Требования:
1. Класс CurrencyManipulator должен иметь метод int getTotalAmount().
2. Метод main класса CashMachine должен вызывать метод getTotalAmount() у объекта класса CurrencyManipulator.


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

 */