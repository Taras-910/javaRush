package com.javarush.task.task34.task3412;
/*
Добавление логирования в класс
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Solution {

    public static final Logger LOGGER = LoggerFactory.getLogger(Solution.class);

    public static void main(String[] args) {

        LOGGER.info("Начало работы программы!!!");

        try {
            LOGGER.warn("Внимание! Программа пытается разделить одно число на другое");
            System.out.println(12/0);
        } catch (ArithmeticException x) {

            LOGGER.error("Ошибка! Произошло деление на ноль!");
        }
    }
}

/*
public class Solution {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);

    private int value1;
    private String value2;
    private Date value3;

    public Solution(int value1, String value2, Date value3) {
        logger.debug("вход в конструктор Solution(int value1("+this.value1+"), String value2("+this.value2+"), Date value3("+this.value3+"))");   // debug 1
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static void main(String[] args) {
        Solution s = new Solution(2,"hello",new Date());
        s.setValue1(1);
        s.setValue2("Hello, java");
        s.setValue3(new Date());
        s.calculateAndSetValue3(5);
        s.printDateAsLong();
        s.printString();
        s.divide(1,0);
    }

    public void calculateAndSetValue3(long value) {
        logger.trace ("Start calculateAndSetValue3");

        value -= 133;
        if (value > Integer.MAX_VALUE) {
            value1 = (int) (value / Integer.MAX_VALUE);
            logger.debug("New value1:"+value1);
        } else {
            value1 = (int) value;
            logger.debug("New value1:"+value1);
        }
    }

    public void printString() {
        logger.trace("в методе printString если value2 != null выводится на печать value2.length() = "+value2.length());              // trace
        if (value2 != null) {
            System.out.println(value2.length());
        }
    }

    public void printDateAsLong() {
        logger.trace("в методе printDaneAsLong если value3 != null выводится на печать value3.getTime()"+value3);// trace
        if (value3 != null) {
            System.out.println(value3.getTime());
        }
    }

    public void divide(int number1, int number2) {
        try {
            logger.trace("вход в метод divide выводится на печать "+number1+"/"+number2);                                              // trace
            System.out.println(number1 / number2);
        } catch (ArithmeticException e) {
            logger.error("Error while divide "+ number1+"/"+number2, e);                                                               // error
        }
    }

    public void setValue1(int value1) {
        this.value1 = value1;
        logger.debug("Start setValue1 value1 = "+value1);                                                              // debug
    }

    public void setValue2(String value2) {
        this.value2 = value2;
        logger.debug("Start setValue2  value2 "+value2);                                                               // debug
    }

    public void setValue3(Date value3) {
        this.value3 = value3;
        logger.debug("Start setValue3  value3 "+value3);                                                               // debug
    }
}
*/
