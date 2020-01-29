package com.javarush.task.task26.task2613;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {

        if (!map.containsKey(currencyCode.toUpperCase()))
            map.put(currencyCode.toUpperCase(), new CurrencyManipulator(currencyCode));
        return map.get(currencyCode.toUpperCase());
    }
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        Collection<CurrencyManipulator> values = map.values();
        return values;
    }
}

/*
CashMachine (8)
Пора привести в порядок наш main, уж очень там всего много, чего не должно быть.
1. Перенеси логику из main в DepositCommand и InfoCommand.
Проверим, что там стало с main? Цикл, в котором спрашиваем операцию у пользователя, а потом вызываем метод у CommandExecutor.
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


CashMachine (3)
1. Создай класс CurrencyManipulator, который будет хранить всю информацию про выбранную валюту.
Класс должен содержать:
1.1 String currencyCode - код валюты, например, USD. Состоит из трех букв.
1.2 Map<Integer, Integer> denominations - это Map<номинал, количество>.
Чтобы можно было посмотреть, к какой валюте относится манипулятор, добавим геттер для currencyCode.
Очевидно, что манипулятор никак не может функционировать без названия валюты,
поэтому добавим конструктор с этим параметром и проинициализируем currencyCode.

2. Валют может быть несколько, поэтому нам понадобится фабрика, которая будет создавать и хранить манипуляторы.
Создай класс CurrencyManipulatorFactory со статическим методом getManipulatorByCurrencyCode(String currencyCode).
В этом методе будем создавать нужный манипулятор, если он еще не существует, либо возвращать ранее созданный.
Регистр при поиске манипулятора валюты не должен учитываться.
Подумай, где лучше хранить все манипуляторы? Маленькая подсказка, поле должно называться map.

Сделайте так, чтобы невозможно было создавать объекты CurrencyManipulatorFactory класса.

Требования:
1. Класс CurrencyManipulator должен быть создан в отдельном файле.
2. Класс CurrencyManipulator должен содержать приватное поле String currencyCode.
3. Класс CurrencyManipulator должен содержать приватное поле Map<Integer, Integer> denominations.
4. Класс CurrencyManipulator должен содержать геттер для поля currencyCode.
5. Класс CurrencyManipulator должен содержать конструктор с одним параметром, инициализирующий поле currencyCode.
6. Класс CurrencyManipulatorFactory должен быть создан в отдельном файле.
7. Класс CurrencyManipulatorFactory должен иметь приватный дефолтный конструктор.
8. Класс CurrencyManipulatorFactory должен содержать приватное статическое поле Map<String, CurrencyManipulator> map.
9. Класс CurrencyManipulatorFactory должен иметь статический метод getManipulatorByCurrencyCode(String currencyCode).

 */