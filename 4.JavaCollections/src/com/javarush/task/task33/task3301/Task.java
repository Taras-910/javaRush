package com.javarush.task.task33.task3301;

public class Task {

    public static void main(String[] args) {

/*
        Operationable operation;
        operation = (x, y) -> x + y;

        System.out.println(operation.calculate(10, 20)); //30
*/
        Operationable operation;
        operation = (x, y) -> x * y;

        System.out.println(calculate(3,5, operation));

    }

    public static double calculate(double a, double b, Operationable operation) {
        return operation.calculate(a, b);
    }

    interface Operationable {
        double calculate(double x, double y);
    }

}

/*class Lambda {
    public static void main (String[] args) {

        System.out.println( calculate(7) ->{ /}));
    }

    public static double calculate(double b, Math method){

        return (a,b) ->
    }
}*/
/*
    Вам надо вписать 3 параметра в сигнатуру метода calculate, дописать 1 команду в return и протестировать вызов этого метода в main.

        Что должен уметь этот метод:

        складывать;
        умножать;
        делить;
        вычитать;
        вычислять корень;
        возводить в степень;
        возводить в степень сумму аргументов поделенную на первое число + 117;
        и все любые другие операции, которые сможете придумать.


        Что нельзя использовать:

        if-else;
        char как указатель операции;
        switch-case;
        и все остальное что вам придет в голову.


        Что можно использовать:

        только лямбды, задание то на них.
        */
