package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class InfoCommand implements Command{

    private ResourceBundle res = ResourceBundle.getBundle(RESOURCE_PATH + "info_en", Locale.getDefault());

    @Override
    public void execute() throws InterruptOperationException {



        Collection<CurrencyManipulator> list = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        if(list.size() == 0) ConsoleHelper.writeMessage(res.getString("no.money"));
        for(CurrencyManipulator manipulator: list){
            int total = manipulator.getTotalAmount();                     // выводим на экран
            if(manipulator.hasMoney()) ConsoleHelper.writeMessage(manipulator.getCurrencyCode()+" - "+total);
            if(!manipulator.hasMoney()) ConsoleHelper.writeMessage(res.getString("no.money"));
        }

//        return new String[0];
    }
}
//no.money=No money available.
/*
CashMachine (8)

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

Пора привести в порядок наш main, уж очень там всего много, чего не должно быть.
1. Перенеси логику из main в DepositCommand и InfoCommand.
Проверим, что там стало с main? Цикл, в котором спрашиваем операцию у пользователя, а потом вызываем метод у CommandExecutor.
И так до бесконечности... надо бы придумать условие выхода из цикла.
Исправь цикл, чтоб он стал do-while. Условие выхода - операция EXIT.
2. Давай запустим прогу и пополним счет на EUR 100 2 и USD 20 6, и посмотрим на INFO.
Ничего не понятно, т.к. создались 2 манипулятора: первый для EUR, второй для USD.
Давай улучшим логику InfoCommand. Надо вывести баланс по каждому манипулятору.
 */