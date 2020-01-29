package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class DepositCommand implements Command{
    private ResourceBundle res = ResourceBundle.getBundle(RESOURCE_PATH +"deposit_en", Locale.getDefault());

    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();        // запрос кода

        String[] line = ConsoleHelper.getValidTwoDigits(currencyCode); // запрос деноминации и кол-ва купюр

        int denomination = 0;
        int count = 0;
        try {
            denomination = Integer.parseInt(line[0]);
            count = Integer.parseInt(line[1]);
        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }

        // записываем в манипулятор
        CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode).addAmount(denomination, count);
        ConsoleHelper.writeMessage(String.format(res.getString("success.format") + "\n",
                count*denomination, currencyCode));

    }
}
//success.format=%d %s was deposited successfully
//invalid.data=Please specify valid data.