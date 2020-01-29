package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;
import static java.util.ResourceBundle.getBundle;

public class LoginCommand implements Command{
    private ResourceBundle validCreditCards = getBundle(RESOURCE_PATH + "verifiedCards", Locale.getDefault());

    private ResourceBundle res = getBundle(RESOURCE_PATH + "login_en", Locale.getDefault());

    @Override
    public void execute() throws InterruptOperationException {
        boolean conditions = false;
        String number = null;
        String pin = null;
        ConsoleHelper.writeMessage(res.getString("before"));
        do {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            number = ConsoleHelper.readString();
            if (!number.matches("[0-9]{12}")) ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            if(number.matches("[0-9]{12}") && validCreditCards.containsKey(number)) {
                ConsoleHelper.writeMessage("Enter pin.");
                pin = ConsoleHelper.readString();
            }
            if (pin != null && !pin.matches("[0-9]{4}"))
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format")+"\n",pin));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            if (pin != null && pin.equals(validCreditCards.getString(number))){
                ConsoleHelper.writeMessage(String.format(res.getString("success.format")+"\n",number));
                conditions = true;
            }
            else{
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format")+"\n",number));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
        }
        while (!conditions);
    }
}
//("not.verified.format"));
//("try.again.or.exit"));
//before=Logging in...
//    specify.data=Please specify your credit card number and pin code or type 'EXIT' for exiting.
//    success.format=Credit card [%s] is verified successfully!
//    not.verified.format=Credit card [%s] is not verified.
//    try.again.or.exit=Please try again or type 'EXIT' for urgent exiting
//    try.again.with.details=Please specify valid credit card number - 12 digits, pin code - 4 digits.

/*
 CashMachine (12)
В задании 11 мы захардкодили номер кредитной карточки с пином, с которыми разрешим работать нашему банкомату.
Но юзеров может быть много. Не будем же мы их всех хардкодить! Если понадобится добавить еще одного пользователя,
то придется передеплоить наше приложение. Есть решение этой проблемы.

Смотри, добавился новый пакет resources, в котором мы будем хранить наши ресурсные файлы.
В этом пакете есть файл verifiedCards.properties, в котором заданы карточки с пинами.
1. В LoginCommand добавь поле private ResourceBundle validCreditCards.
При объявлении инициализируй это поле данными из файла verifiedCards.properties.
Почитай в инете, как это делается для ResourceBundle.
Важно: путь к ресурсу verifiedCards.properties строй динамически,
для этого используй у класса CashMachine метод getPackage()
2. Замени хардкоженные данные кредитной карточки и пина на проверку наличия данных в ресурсе verifiedCards.properties.
Требования:
1. LoginCommand должен содержать приватное поле ResourceBundle validCreditCards.
2. Поле validCreditCards должно быть проинициализировано из файла verifiedCards.properties.
3. Используй проверку кредитной карточки и пина через verifiedCards.properties.


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
 */

/*
public class LoginCommand implements Command{
    private ResourceBundle validCreditCards;

    @Override
    public void execute() throws InterruptOperationException {
        validCreditCards = getBundle(CashMachine.class.getPackage().getName() + "/resources.verifiedCards",
                        Locale.getDefault());
        boolean conditions = false;
        String number = null;
        String pin = null;
        String pinProperty = null;
        do {
            ConsoleHelper.writeMessage("Enter card number.");
            number = ConsoleHelper.readString();
            if (!number.matches("[0-9]{12}")) ConsoleHelper.writeMessage("Wrong data.");
            else if(validCreditCards.containsKey(number)){
                ConsoleHelper.writeMessage("Enter pin.");
                pin = ConsoleHelper.readString();
                if (!pin.matches("[0-9]{4}")) ConsoleHelper.writeMessage("Wrong data.");
                else{
                    pinProperty  = validCreditCards.getString(number);
                    if (pin.equals(pinProperty)){
                        ConsoleHelper.writeMessage("Login was successful.");
                        conditions = true;
                        return;
                    }
                }
            }
        }
        while (!conditions);
    }
}




    public void execute() throws InterruptOperationException {
        validCreditCards =
                getBundle(CashMachine.class.getPackage().getName() + "/resources.verifiedCards",
//                getBundle("com/javarush/task/task26/task2613/resources/verifiedCards",
                Locale.getDefault());

//        Enumeration bundleKeys = validCreditCards.getKeys();

//        while (bundleKeys.hasMoreElements()) {
//            String key = (String)bundleKeys.nextElement();
//            String value  = validCreditCards.getString(key);
//            System.out.println("key = " + key + ", " +
//                    "value = " + value);
//        }

        boolean conditions = false;
        String number = null;
        String pin = null;
        String pinProperty = null;
        do {
            ConsoleHelper.writeMessage("Enter card number.");
            number = ConsoleHelper.readString();
            if (!number.matches("[0-9]{12}")) ConsoleHelper.writeMessage("Wrong data.");
            else if(validCreditCards.containsKey(number)){
                ConsoleHelper.writeMessage("Enter pin.");
                pin = ConsoleHelper.readString();
                if (!pin.matches("[0-9]{4}")) ConsoleHelper.writeMessage("Wrong data.");
                else{
                    pinProperty  = validCreditCards.getString(number);
                    if (pin.equals(pinProperty)){
                        ConsoleHelper.writeMessage("Login was successful.");
                        conditions = true;
                        return;
                    }
                }
            }

//key = 123456789012, value = 1234
//key = 234567890123, value = 2345
//key = 345678901234, value = 3456



        } while (!conditions);

    }
}


 */