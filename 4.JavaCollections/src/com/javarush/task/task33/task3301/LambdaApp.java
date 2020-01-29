package com.javarush.task.task33.task3301;

/*public class LambdaApp {

    public static void main(String[] args) {

        Printable printer = s->System.out.println(s);
        printer.print("Hello Java!");
    }
}*/
public class LambdaApp {

    public static void main(String[] args) {

        Expression func = (n)-> n%2!=0;
        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        System.out.println(sum(nums, func)); // 20
    }
    private static int sum (int[] numbers, Expression func)
    {
        int result = 0;
        for(int i : numbers)
        {
            if (func.isEqual(i))
                result += i;
        }
        return result;
    }
}

interface Expression{
    boolean isEqual(int n);
}


