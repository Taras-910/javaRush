package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;
import static java.util.ResourceBundle.getBundle;

class WithdrawCommand implements Command {
    private ResourceBundle res = getBundle(RESOURCE_PATH + "withdraw_en", Locale.getDefault());

    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        ConsoleHelper.writeMessage(res.getString("specify.amount"));
        Map<Integer, Integer> withdrawMap = null;
        int amount;

        ConsoleHelper.writeMessage(res.getString("before"));
        while (true) {
            try {
                amount = Integer.parseInt(ConsoleHelper.readString());
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if (amount <= 0) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if (!currencyManipulator.isAmountAvailable(amount)) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            try {
                withdrawMap = currencyManipulator.withdrawAmount(amount);
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                continue;
            }

            for (Map.Entry entry : withdrawMap.entrySet()
            ) {
                ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
            }
            ConsoleHelper.writeMessage(String.format(res.getString("success.format") + "\n",
                    amount, currencyCode));
            break;
        }
    }
}
//before
//*success.format=%d %s was withdrawn successfully
//*specify.amount=Please specify integer amount for withdrawing.
//*specify.not.empty.amount=Please specify valid positive integer amount for withdrawing.
//*not.enough.money=Not enough money on your account, please try again
//*exact.amount.not.available=Exact amount is not available

/*
CashMachine (10)
Сегодня мы займемся командой WithdrawCommand - это самая сложная операция.
1. Реализуй следующий алгоритм для команды WithdrawCommand:
1.1. Считать код валюты (метод уже есть).
1.2. Получить манипулятор для этой валюты.
1.3. Пока пользователь не введет корректные данные выполнять:
1.3.1. Попросить ввести сумму.
1.3.2. Если введены некорректные данные, то сообщить об этом пользователю и вернуться к п. 1.3.
1.3.3. Проверить, достаточно ли средств на счету.
Для этого в манипуляторе создай метод boolean isAmountAvailable(int expectedAmount), который вернет true,
если денег достаточно для выдачи.
Если недостаточно, то вернуться к п. 1.3.
1.3.4. Списать деньги со счета. Для этого в манируляторе создай метод
Map<Integer, Integer> withdrawAmount(int expectedAmount), который вернет карту HashMap<номинал, количество>.
Подробно логику этого метода смотри в п.2.
1.3.5. Вывести пользователю результат из п. 1.3.4. в следующем виде:
<табуляция>номинал - количество.
Сортировка - от большего номинала к меньшему.
Вывести сообщение об успешной транзакции.
1.3.6. Перехватить исключение NotEnoughMoneyException, уведомить юзера о нехватке банкнот и вернуться к п. 1.3.

2. Логика основного метода withdrawAmount:
2.1. Внимание!!! Метод withdrawAmount должен возвращать минимальное количество банкнот,
которыми набирается запрашиваемая сумма.
Используйте Жадный алгоритм (use google).
Если есть несколько вариантов, то использовать тот, в котором максимальное количество банкнот высшего номинала.
Если для суммы 600 результат - три банкноты: 500 + 50 + 50 = 200 + 200 + 200, то выдать первый вариант.

Пример, надо выдать 600.
В манипуляторе есть следующие банкноты:
500 - 2
200 - 3
100 - 1
50 - 12

Результат должен быть таким:
500 - 1
100 - 1

т.е. всего две банкноты (это минимальное количество банкнот) номиналом 500 и 100.

2.2. Мы же не можем одни и те же банкноты выдавать несколько раз, поэтому
если мы нашли вариант выдачи денег (п.2.1. успешен), то вычесть все эти банкноты из карты в манипуляторе.

2.3. метод withdrawAmount должен кидать NotEnoughMoneyException, если купюрами невозможно выдать запрашиваемую сумму.

Пример, надо выдать 600.
В манипуляторе есть следующие банкноты:
500 - 2
200 - 2
Результат - данными банкнотами невозможно выдать запрашиваемую сумму. Кинуть исключение.
Не забудьте, что в некоторых случаях картой кидается ConcurrentModificationException.
Требования:
1. Класс CurrencyManipulator должен содержать метод boolean isAmountAvailable(int expectedAmount).
2. Метод isAmountAvailable должен возвращать true, если денег достаточно для выдачи.
3. Класс CurrencyManipulator должен содержать метод Map<Integer, Integer> withdrawAmount(int expectedAmount).
4. Метод withdrawAmount должен возвращать карту согласно заданию.
5. Метод execute класса WithdrawCommand должен реализовывать алгоритм для команды WithdrawCommand, согласно заданию.
 */



/* простой жадный алгоритм

class WithdrawCommand implements Command{
    @Override
    public void execute() throws InterruptOperationException {

        String currencyCode = ConsoleHelper.askCurrencyCode();        // запрос кода

        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int total = currencyManipulator.getTotalAmount();             // подсчитывам общую сумму данной валюты
        boolean conditions = false;

        while (!conditions) {
            if (total > 0) {       // общая сумма данной валюты

                writeMessage("Enter the requested amount.");
                String line = readString().trim();                                    //  !!!!!!!!

                if (line != null && line.matches("\\d+")) {
                    int data = Integer.parseInt(line);

                    if(data <= 0) writeMessage("Incorrect data!");
                    else {

                        if (currencyManipulator.isAmountAvailable(data)) { // если средств на счету больше чем запрос
                            conditions = true;
                            Map<Integer, Integer> map = new TreeMap<>(Comparator.reverseOrder());

                            try {
                                map.putAll(currencyManipulator.withdrawAmount(data));

                                for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
                                    ConsoleHelper.writeMessage(pair.getKey() + " - " + pair.getValue());
                                }
                                ConsoleHelper.writeMessage("Transaction was successful.\n");

                            } catch (NotEnoughMoneyException | ConcurrentModificationException e) {
                                ConsoleHelper.writeMessage("Request cannot be completed, so as no suitable options");
                            }


                            // если приходит пустая мапа (запрос меньше минимального номинала)
                        } else {
                            try {
                                throw new NotEnoughMoneyException();
                            } catch (NotEnoughMoneyException e) {
                                writeMessage("There is not enough money in the account for this transaction!");
                            }
                        }
                    }
                } else writeMessage("Incorrect data!");
            }else{
                writeMessage("This count is empty.");
                conditions = true;
            }
        }



//        writeMessage(currencyCode+" - "+total);          // выводим на экран
    }
}


 */