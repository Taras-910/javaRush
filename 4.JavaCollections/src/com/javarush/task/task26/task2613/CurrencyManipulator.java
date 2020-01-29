package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private String currencyCode;                  //код валюты, например, USD. Состоит из трех букв
    private Map<Integer, Integer> denominations = new HashMap<>();  //это Map<номинал, количество>

    // для списания средств со счета, HashMap<номинал, количество>
    public Map<Integer, Integer> withdrawAmount(int expectedAmount)
            throws ConcurrentModificationException, NotEnoughMoneyException {

        Map<Integer, Map<Integer, Integer>> allResults = new TreeMap<>(Comparator.reverseOrder()); //
        Map<Integer, Integer> tempMap = new TreeMap<>(Comparator.reverseOrder());
        tempMap.putAll(denominations);
        Map<Integer, Integer> map = new TreeMap<>(tempMap);
        int commonNumberBills = 0;
        List<Map<Integer, Integer>> list = new ArrayList();
        for(Map.Entry<Integer, Integer> pair: tempMap.entrySet()){

            commonNumberBills = commonNumberBills + pair.getValue();  // общее число купюр
            int number = 0; // проверка достаточно ли денег в этой усеченной мапе
            for (Map.Entry<Integer, Integer> p : map.entrySet()) {
                number += p.getValue()*p.getKey();
            }
            if((!map.isEmpty() || map != null) && number >= expectedAmount) list.add(new TreeMap(map));
            if(map.size() > 1) map.remove(pair.getKey());
        }
        for (int i = 0; i < list.size(); i++) {
            map = tempResult(expectedAmount, list.get(i));                                  // передаем в метод

            if(i == list.size() - 1 && allResults.isEmpty() && map == null) throw new NotEnoughMoneyException();

            if (map != null) {
                int count = 0;
                int maxKey = 0;
                int maxKeyResult = 0;
                for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
                    count += pair.getValue();
                    maxKey = maxKey > pair.getKey() ? maxKey : pair.getKey();
                }
                if(!allResults.isEmpty() && allResults.containsKey(count)) {
                    for (Map.Entry<Integer, Integer> pair : allResults.get(count).entrySet()) {
                        maxKeyResult = maxKeyResult > pair.getKey() ? maxKeyResult : pair.getKey();
                    }
                }
                if(maxKey > maxKeyResult) allResults.put(count, map);
            }
        }
        // выбор мапы с минимальным числом купюр
        int min = commonNumberBills;
        for (Map.Entry<Integer, Map<Integer, Integer>> pair : allResults.entrySet()) {
            min = min < pair.getKey() ? min : pair.getKey();
        }
        for (Map.Entry<Integer, Integer> pair : allResults.get(min).entrySet()) {     //  финальное уменьшение выбранных купюр
            int key = pair.getKey();
            int selectedValue = pair.getValue();
            denominations.put(key, denominations.get(key) - selectedValue);
        }
        return allResults.get(min);
    }


    public Map<Integer,Integer> tempResult(int expectedAmount, Map<Integer,Integer>map){
        Map<Integer, Integer> resultMap = new TreeMap<>(Comparator.reverseOrder());
        Map<Integer, Integer> tempMap = new TreeMap<>(Comparator.reverseOrder());
        tempMap.putAll(map);

        int currentExpectedAmount = expectedAmount; // запрашиваемая сумма
        int currentDenomination = 0;
        int totalNumberBills;
        int requestNumberBills = 0;
        int rest = 0;                               // промежуточний остаток
        boolean conditions = false;

        do {
            int maxDenomination = 0;
            int minDenomination = Integer.MAX_VALUE;

            for (Map.Entry<Integer, Integer> pair : tempMap.entrySet()) {
                currentDenomination = pair.getKey();

                if (pair.getValue() != 0) {
                    if (currentDenomination <= currentExpectedAmount) {  // если номинал муньше чем запрашиваемая сумма
                        maxDenomination = currentDenomination > maxDenomination ? currentDenomination : maxDenomination;
                        minDenomination = currentDenomination < minDenomination ? currentDenomination : minDenomination;
                    } else tempMap.put(currentDenomination, 0);
                }
            }

            int sumTest = 0;
            for (Map.Entry<Integer, Integer> pair : tempMap.entrySet()) {
                int s = pair.getValue();
                sumTest += s;
            }
            if (sumTest != 0 && currentExpectedAmount != 0) {                     // если в мапе остались подходящие купюры

                totalNumberBills = map.get(maxDenomination);             // кол-во купюр данной деноминации

                if (currentExpectedAmount < totalNumberBills * maxDenomination) {
                    requestNumberBills = currentExpectedAmount / maxDenomination;// кол-во запраш-х купюр данной деноминации
                    rest = currentExpectedAmount % maxDenomination;
                } else {
                    rest = currentExpectedAmount - totalNumberBills * maxDenomination;
                    requestNumberBills = totalNumberBills;
                }
                currentExpectedAmount = rest; // остаток ктр еще нужно добрать
                resultMap.put(maxDenomination, requestNumberBills);
                tempMap.put(maxDenomination, 0);

                if (currentExpectedAmount == 0) {
                    conditions = true;
                    return new HashMap<>(resultMap);
                }
            }
            else{
                conditions = true;
                return null;      // нет подходящих купюр
            }
        } while (!conditions);
        return null;
    }


    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public boolean hasMoney(){
        return getTotalAmount() > 0;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count){
        if (denominations.containsKey(denomination)) count = count + denominations.get(denomination);
        denominations.put(denomination, count);
    }
    public int getTotalAmount(){
        int total = 0;
        for(Map.Entry<Integer, Integer> pair: denominations.entrySet()){
            total += pair.getKey() * pair.getValue();
        }
        return total;
    }

    // проверка, достаточно ли средств на счету
    public boolean isAmountAvailable(int expectedAmount){
        if(expectedAmount <= getTotalAmount()) return true;
        return false;
    }


}
/*
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


/* простой жадный алгоритм
                                                           // для списания средств со счета, HashMap<номинал, количество>
    public Map<Integer, Integer> withdrawAmount(int expectedAmount)
    throws ConcurrentModificationException, NotEnoughMoneyException {

        Map<Integer, Integer> resultMap = new TreeMap<>(Comparator.reverseOrder());
        Map<Integer, Integer> AllResults = new TreeMap<>(Comparator.reverseOrder());
//        resultMap.putAll(denominations);
        Map<Integer, Integer> tempMap = new TreeMap<>(Comparator.reverseOrder());
        tempMap.putAll(denominations);

        int currentExpectedAmount = expectedAmount; // запрашиваемая сумма
        int currentDenomination;
        int totalNumberBills;
        int requestNumberBills = 0;
        int rest = 0;                               // промежуточний остаток
        boolean conditions = false;

//исключения бросать и в withdrawAmount и в WithdrawCommand
//        ConcurrentModificationException упомянули
//
//Метод получился не более 10 строк.
//1. Заменил объявление поля denominations с HashMap на TreeMap<>(Comparator.reverseOrder()).
//2. Создал ещё один TreeMap<>(Comparator.reverseOrder()) для результата.
//3. Для каждого номинала купюры забираю по 1 из хранилища и добавляю 1 в результат.
//4. Нулевые записи оставляю. Не увидел, где нам они помешают.
//5. Если номинал купюры превышает остаток требуемой суммы, перехожу к следующему номиналу.
//6. Если после прохода по всем номиналам остаток требуемой суммы > 0, кладу всё назад и выбрасываю исключение.

        do {
            int maxDenomination = 0;

            for (Map.Entry<Integer, Integer> pair : tempMap.entrySet()) {
                currentDenomination = pair.getKey();

                if (pair.getValue() != 0) {
                    if (currentDenomination <= currentExpectedAmount) {  // если номинал муньше чем запрашиваемая сумма
                        maxDenomination = currentDenomination > maxDenomination ? currentDenomination : maxDenomination;
                    } else tempMap.put(currentDenomination, 0);
                }
            }

            int sumTest = 0;
            for (Map.Entry<Integer, Integer> pair : tempMap.entrySet()) {
                sumTest += pair.getValue();
            }
            if (sumTest != 0 && currentExpectedAmount != 0) {                     // если в мапе остались подходящие купюры

                totalNumberBills = denominations.get(maxDenomination);             // кол-во купюр данной деноминации

                if (currentExpectedAmount < totalNumberBills * maxDenomination) {
                    requestNumberBills = currentExpectedAmount / maxDenomination;// кол-во запраш-х купюр данной деноминации
                    rest = currentExpectedAmount % maxDenomination;
                } else {
                    rest = currentExpectedAmount - totalNumberBills * maxDenomination;
                    requestNumberBills = totalNumberBills;
                }

                currentExpectedAmount = rest; // остаток ктр еще нужно добрать

                resultMap.put(maxDenomination, requestNumberBills);

                tempMap.put(maxDenomination, 0);

                if (currentExpectedAmount == 0) {

                    conditions = true;
//в конце поиска удаляем выданные к-ры (resultMap) из б-та (denominations)
                    for (Map.Entry<Integer, Integer> pair : resultMap.entrySet()) {
                        currentDenomination = pair.getKey();
                        denominations.put(currentDenomination, denominations.get(currentDenomination) - pair.getValue());
                    }
                    return new HashMap<>(resultMap);

                }

            }
            else{
                throw new NotEnoughMoneyException();
            }

        } while (!conditions);



        return null;
    }

 */