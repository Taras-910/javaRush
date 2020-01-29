package com.javarush.task.task34.task3404;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recurse("tan(2025^0.5)", 0);
        solution.recurse("sin(2*(-5+1.5*4)+28)", 0); //expected output 0.5 6
    }
    public void recurse(final String expression, int countOperation) {
        Pattern thisOnlyNumber = Pattern.compile("^-?[0-9]+([,.][0-9]+)?");        // это число?
        Matcher m = thisOnlyNumber.matcher(expression);
        if (m.matches()) {                                                          //  ----------окончание программы------------
            double value = Double.parseDouble(expression);
            String pattern = "####.##";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            String output = myFormatter.format(value);
            System.out.print(output+ " "+ countOperation); // если осталось только число - вывести на экран
        } else {                                                                    // если в скобках выражение а не простое число
            List<String> listElements = new ArrayList<>();                          // все операции и элементы внутри скобок
            List<String> listOnlyNumbers = new ArrayList<>();                       // все числа внутри скобок
            String newIntoBracket = null;                                           // новое выражение вместо скобок
            String newExpression = null;
            int allElements = 0;                                                    // количество операций и элементов внутри скобок
            int onlyNumbers = 0;
            int onlyOperations = 0;
            double result = 0.0;                                                    // результат вичисления внутри скобок
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            String pattern = "####.##";                                              // устанавливаем точку вместо стандартной запятой
            DecimalFormat df = new DecimalFormat(pattern, otherSymbols);            // форматирование
            boolean bracketExist = false;
            int bracketOpen =  0;                                                   //  индекс открывающих скобок если выражение не имеет скобок
            int bracketOpenFunction = 0;
            int bracketClose = expression.length();                                 //  индекс закрывающих ------------- // --------------

            Pattern p = Pattern.compile("^.*\\(.*\\).*");                           // есть скобки?
            Matcher mt = p.matcher(expression);
            if (mt.matches()) {                                                     //
                bracketOpen = expression.lastIndexOf("(") + 1;                  // индекс последних открывающих скобок
                bracketClose = expression.indexOf(")", bracketOpen);            // индекс первых закрывающих скобок
                bracketExist = true;
            }
            String intoBracket = expression.substring(bracketOpen, bracketClose);   // код внутри скобок
            StringTokenizer st = new StringTokenizer(intoBracket, "^/*-+", true);
            allElements = st.countTokens();                                         // внутри скобок количество чисел и операций
            while (st.hasMoreTokens()) {
                String stringTokenizer = st.nextToken();
                listElements.add(stringTokenizer);                                   // список элементов внутри скобок
            }
            StringTokenizer st2 = new StringTokenizer(intoBracket, "()^/*-+", false);
            onlyNumbers = st2.countTokens();                                         // внутри скобок количество чисел
            while (st2.hasMoreTokens()) {
                listOnlyNumbers.add(st2.nextToken());                                 // список чисел внутри скобок
            }
            onlyOperations = allElements - onlyNumbers;
            String beforBracket = "aaa";
            if (bracketOpen > 3) beforBracket = expression.substring(bracketOpen - 4, bracketOpen - 1); // проверка на функцию??????????????
            boolean function = false;
            int indexSin = -1;
            int indexCos = -1;
            int indexTan = -1;
            if (beforBracket.matches("sin")) indexSin = 0;
            if (beforBracket.matches("cos")) indexCos = 0;
            if (beforBracket.matches("tan")) indexTan = 0;
            if (indexSin == 0 || indexCos == 0 || indexTan == 0) {
                bracketOpenFunction = bracketOpen - 3;
                function = true;
            }
            if (!function||onlyOperations>0) {                                        // если внутри скобок не аргумент функции
                if (listOnlyNumbers.size() == 1) {                                    // в выражении только одна цифра
                    newIntoBracket = intoBracket;
                } else {
                    int cyсle = onlyOperations;
                    for (int k = 0; k < cyсle; k++) {                                 // ---------------начало цикла
                        int indexPow = -1;                                            // количество операций
                        int indexDivide = -1;
                        int indexMultiply = -1;
                        int indexSubstract = -1;
                        int indexAdd = -1;
                        if (listElements.get(0).equals("-")){
                            listElements.add(0, "0");                    // добавляем "0" если вначале "-"
                        }
                        for (int i = 0; i < listElements.size() - 1; i++) {            // подряд  "-" "-" или "+" "+"
                            if (listElements.get(i).equals("-") && listElements.get(i + 1).equals("-") ||
                                    listElements.get(i).equals("+") && listElements.get(i + 1).equals("+")) {
                                listElements.set(i + 1, "+");
                                listElements.remove(i);
                                cyсle--;
                                countOperation++;
                            }
                        }
                        for (int i = 0; i < listElements.size() - 1; i++) {
                            if (listElements.get(i).equals("+") && listElements.get(i + 1).equals("-") ||
                                    listElements.get(i).equals("-") && listElements.get(i + 1).equals("+")) {
                                listElements.set(i + 1, "+");                          // подряд  "+" "-" или "-" "+"
                                listElements.remove(i);
                                cyсle--;
                                countOperation++;
                            }
                        }
                        for (int i = 0; i < listElements.size() - 1; i++) {            // если "/" "*" "^" стоят перед "-"
                            if (listElements.get(i).equals("-")) {
                                if (listElements.get(i - 1).equals("/") || listElements.get(i - 1).equals("*")) {
                                    listElements.set(i, listElements.get(i - 1));      // через сдвиг переносим знак на два элемента вперед
                                    listElements.set(i - 1, listElements.get(i - 2));
                                    listElements.set(i - 2, "-");
                                }
                            }
                        }
                        for (int i = 0; i < listElements.size() - 1; i++) {            // если "-" стоят перед числом, ктр перед "^" или после "^"
                            if (listElements.get(i).equals("-")) {
                                if((listElements.get(i - 1).equals("^"))|| (i+2<listElements.size()-1&&listElements.get(i+2).equals("^")) ) {
                                    Double t = -Double.parseDouble(listElements.get(i + 1));     // change sign
                                    listElements.set(i + 1, t.toString());
                                    listElements.remove(i);
                                    cyсle--;
                                    countOperation++;
                                }
                            }
                        }
                        for (int i = 0; i < listElements.size(); i++) {
                            if (listElements.get(i).equals("^")) indexPow = i;
                            if (listElements.get(i).equals("/")) indexDivide = i;
                            if (listElements.get(i).equals("*")) indexMultiply = i;
                            if (listElements.get(i).equals("-")) indexSubstract = i;
                            if (listElements.get(i).equals("+")) indexAdd = i;
                        }
                        result = 0;
                        int index = 0;                                                         // индекс текущего элемента
                        String param1, param2;
                        if (indexPow != -1) {
                            index = indexPow;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Math.pow(Double.parseDouble(param1), Double.parseDouble(param2));
                        } else if (indexDivide != -1) {
                            index = indexDivide;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) / Double.parseDouble(param2);
                        } else if (indexMultiply != -1) {
                            index = indexMultiply;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) * Double.parseDouble(param2);
                        } else if (indexSubstract != -1) {
                            index = indexSubstract;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) - Double.parseDouble(param2);
                        } else if (indexAdd != -1) {
                            index = indexAdd;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) + Double.parseDouble(param2);
                        }
                        countOperation++;
                        newIntoBracket = df.format(result);
                        listElements.set(index, newIntoBracket);
                        listElements.remove(index + 1);
                        listElements.remove(index - 1);
                    }
                }
                if(bracketExist&&!function){                                                       // усли существовали скобки
                    newExpression = new StringBuilder(expression).replace(bracketOpen - 1, bracketClose + 1, newIntoBracket).toString();
                }else{
                    newExpression = new StringBuilder(expression).replace(bracketOpen, bracketClose , newIntoBracket).toString();
                }
                System.out.println("\n\n");
                recurse(newExpression, countOperation);
            } else {                                                                               // если внутри скобок аргумент - функции
                if (indexSin != -1) {
                    result = Math.sin(Math.toRadians(Double.parseDouble(listElements.get(0))));
                } else if (indexCos != -1) {
                    result = Math.cos(Math.toRadians(Double.parseDouble(listElements.get(0))));
                } else if (indexTan != -1) {
                    result = Math.tan(Math.toRadians(Double.parseDouble(listElements.get(0))));
                }
 //               function = false;
                countOperation++;
                newIntoBracket = df.format(result);
                newExpression = new StringBuilder(expression).replace(bracketOpenFunction - 1, bracketClose + 1, newIntoBracket).toString();
                recurse(newExpression, countOperation);
            }
        }
    }
    public Solution() {
        //don't delete
    }
}








/*
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();


//        solution.recurse("4*(2)", 0);

 //       solution.recurse("-2^(-2)", 0); //expected output 0.5 6
        solution.recurse("tan(2025 ^ 0.5)", 0); //expected output 0.5 6
//        solution.recurse("sin(2*(-5+1.5*4)+28)", 0); //expected output 0.5 6
//        solution.recurse("sin(2*(-3+4*5.5+(35-sin(0.7))-49))", 0); //expected output 0.5 6
//        solution.recurse("(2025 ^ 0.5-20/(5*2))", 0); //expected output 0.5 6
//        solution.recurse("tan(2025 ^ 0.5)", 0); //expected output 0.5 6
//        solution.recurse("tan(2*sin(0.05+0.2^2)-95.5)", 0); //expected output 0.5 6
//        solution.recurse("sin(2*(-5+1.5*44.5+ 10*2 --4)+28)", 0); //expected output 0.5 6
//        solution.recurse("sin(2*(-3+4*5.5+(35-sin(0.7))+9))", 0); //expected output 0.5 6
//        solution.recurse("sin(2*(-5*tan(25)+1.5*4)+28)", 0); //expected output 0.5 6
    }
    //("^[-?\\(?]+[0-9]+([,.][0-9]+)?(\\))?"); -(-202)
    //("^-?\\(*[0-9]+([,.][0-9]+)?(\\))*"); (-202)
    public void recurse(final String expression, int countOperation) {
        int countSign = countOperation;
        System.out.printf("76 expression: " + expression + "\n");
//        Pattern thisOnlyNumber = Pattern.compile("^-?[0-9]+([,.][0-9]+)?");        // это число?
        Pattern thisOnlyNumber = Pattern.compile("^-?[0-9]+([,.][0-9]+)?");        // это число?
        Matcher m = thisOnlyNumber.matcher(expression);
        if (m.matches()) {                             //  ----------окончание программы------------
            double value = Double.parseDouble(expression);
            String pattern = "####.##";
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            String output = myFormatter.format(value);
            System.out.print(output+ " "+ countOperation); // если осталось только число - вывести на экран
        } else {                                                                    // если в скобках выражение а не простое число
//            System.out.println("83 test");
            List<String> listElements = new ArrayList<>();                          // все операции и элементы внутри скобок
//            List<String> listElementsDup = new ArrayList<>();                       // все операции и элементы внутри скобок (дупликат)
            List<String> listOnlyNumbers = new ArrayList<>();                       // все числа внутри скобок
            String newIntoBracket = null;                                           // новое выражение вместо скобок
            String newExpression = null;
            int allElements = 0;                                                    // количество операций и элементов внутри скобок
            int onlyNumbers = 0;
            int onlyOperations = 0;
            double result = 0.0;                                                    // результат вичисления внутри скобок
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);//
            String pattern = "##0.00";                                            // устанавливаем точку вместо стандартной запятой
            DecimalFormat df = new DecimalFormat(pattern, otherSymbols);            // форматирование
            boolean bracketExist = false;
            int bracketOpen =  0;                                                   //  индекс открывающих скобок если выражение не имеет скобок
            int bracketOpenFunction = 0;
            int bracketClose = expression.length();                                 //  индекс закрывающих ------------- // --------------

            Pattern p = Pattern.compile("^.*\\(.*\\).*");                           // есть скобки?
            Matcher mt = p.matcher(expression);
            if (mt.matches()) {                                                     //
                bracketOpen = expression.lastIndexOf("(") + 1;                  // индекс последних открывающих скобок
                bracketClose = expression.indexOf(")", bracketOpen);            // индекс первых закрывающих скобок
                bracketExist = true;
//                System.out.println("test 106");
            }
//            System.out.println("105 bracketOpen: "+bracketOpen+" bracketClose: "+bracketClose);
            String intoBracket = expression.substring(bracketOpen, bracketClose);   // код внутри скобок
            StringTokenizer st = new StringTokenizer(intoBracket, "^/*-+", true);
            allElements = st.countTokens();                                         // внутри скобок количество чисел и операций
            while (st.hasMoreTokens()) {
                String stringTokenizer = st.nextToken();
                listElements.add(stringTokenizer);                                    //          список элементов внутри скобок
//                listElementsDup.add(stringTokenizer);                                 // дупликат список элементов внутри скобок
            }
//            System.out.println("112 elements: "+elements);
            StringTokenizer st2 = new StringTokenizer(intoBracket, "()^/*-+", false);
            onlyNumbers = st2.countTokens();                                     // внутри скобок количество чисел
            while (st2.hasMoreTokens()) {
                listOnlyNumbers.add(st2.nextToken());                                 // список чисел внутри скобок
            }
            onlyOperations = allElements - onlyNumbers;
            String beforBracket = "aaa";
            if (bracketOpen > 3) beforBracket = expression.substring(bracketOpen - 4, bracketOpen - 1); // проверка на функцию??????????????
            boolean function = false;
            int indexSin = -1;
            int indexCos = -1;
            int indexTan = -1;
            if (beforBracket.matches("sin")) indexSin = 0;
            if (beforBracket.matches("cos")) indexCos = 0;
            if (beforBracket.matches("tan")) indexTan = 0;
            if (indexSin == 0 || indexCos == 0 || indexTan == 0) {
                bracketOpenFunction = bracketOpen - 3;
                function = true;
            }
            if (!function||onlyOperations>0) {                                                                  // если внутри скобок не аргумент функции
//                System.out.println("141 переход направо function: "+function);
                if (listOnlyNumbers.size() == 1) {                                            // в выражении только одна цифра
//                    System.out.println("141 "+(bracketOpen-1) +" "+ (bracketClose+1) +" "+ intoBracket+" new = "+newIntoBracket);
                    newIntoBracket = intoBracket;
                } else {
                    int cyсle = onlyOperations;
                    for (int k = 0; k < cyсle; k++) {                    // ---------------начало цикла
                        System.out.println("139 в начале цикла:      счетчик ("+k+") list(" + listElements.size() + ") " + listElements.toString());

                        int indexPow = -1;                                                     // количество операций
                        int indexDivide = -1;
                        int indexMultiply = -1;
                        int indexSubstract = -1;
                        int indexAdd = -1;

                        if (listElements.get(0).equals("-")){
                            listElements.add(0, "0");                    // добавляем "0" если вначале "-"
                            System.out.println("154 добавили \"0\""+ listElements.toString());
                        }
                        System.out.println("150                 до операции -- ++ countOperation "+countOperation+" result: "+result);
                        for (int i = 0; i < listElements.size() - 1; i++) {              // подряд  "-" "-" или "+" "+"
                            if (listElements.get(i).equals("-") && listElements.get(i + 1).equals("-") || listElements.get(i).equals("+") &&
                             listElements.get(i + 1).equals("+")) {
                                listElements.set(i + 1, "+");
                                listElements.remove(i);
                                cyсle--;
                                countOperation++;
                            }
                        }
//                        System.out.println("156 после операции  -- ++ countOperation "+countOperation+" result: "+result);
//                        System.out.println("159  до операции + -  - + countOperation "+countOperation+" result: "+result);
                        for (int i = 0; i < listElements.size() - 1; i++) {
                            if (listElements.get(i).equals("+") && listElements.get(i + 1).equals("-") || listElements.get(i).equals("-") &&
                             listElements.get(i + 1).equals("+")) {
                                listElements.set(i + 1, "+");                                   // подряд  "+" "-" или "-" "+"
                                listElements.remove(i);
                                cyсle--;
                                countOperation++;
                            }
                        }
//                        System.out.println("166             после операц + -  - + countOperation "+countOperation+" result: "+result);

                        for (int i = 0; i < listElements.size() - 1; i++) {                             // если "/" "*" "^" стоят перед "-"
                            if (listElements.get(i).equals("-")) {
                                if (listElements.get(i - 1).equals("/") || listElements.get(i - 1).equals("*")) {
                                    listElements.set(i, listElements.get(i - 1));                // через сдвиг переносим знак на два элемента вперед
                                    listElements.set(i - 1, listElements.get(i - 2));
                                    listElements.set(i - 2, "-");
                                }
                            }
                        }
                        for (int i = 0; i < listElements.size() - 1; i++) {                             // если "-" стоят перед числом, ктр перед "^" или после "^"
                            if (listElements.get(i).equals("-")) {
                                if((listElements.get(i - 1).equals("^"))|| (i+2<listElements.size()-1&&listElements.get(i+2).equals("^")) ) {
                                 // если  "^" стоят перед "-"
                                    Double t = -Double.parseDouble(listElements.get(i + 1));            // change sign
                                    listElements.set(i + 1, t.toString());
                                    listElements.remove(i);
                                    cyсle--;
                                    countOperation++;
                                }
                            }
                        }

//                        System.out.println("178 цикл перед логикой: (" + listElements.size() + ") " + listElements.toString());
                        for (int i = 0; i < listElements.size(); i++) {
                            if (listElements.get(i).equals("^")) indexPow = i;
                            if (listElements.get(i).equals("/")) indexDivide = i;
                            if (listElements.get(i).equals("*")) indexMultiply = i;
                            if (listElements.get(i).equals("-")) indexSubstract = i;
                            if (listElements.get(i).equals("+")) indexAdd = i;
                        }
                        result = 0;
                        int index = 0;                                                         // индекс текущего элемента
                        String param1, param2;
                        System.out.println("^   :" + indexPow);
                        System.out.println("/   :" + indexDivide);
                        System.out.println("*   :" + indexMultiply);
                        System.out.println("-   :" + indexSubstract);
                        System.out.println("+   :" + indexAdd);
//                        System.out.println("199 перед oперациями list: " + listElements.size() + " result: " +result);
                        System.out.println("195       до арифмет операций countOperation "+countOperation+" result: "+result);
                        if (indexPow != -1) {
                            System.out.println("211 до операции Pow: "+listElements.toString());
                            index = indexPow;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Math.pow(Double.parseDouble(param1), Double.parseDouble(param2));
                        } else if (indexDivide != -1) {
                            index = indexDivide;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) / Double.parseDouble(param2);
                        } else if (indexMultiply != -1) {
                            index = indexMultiply;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) * Double.parseDouble(param2);
                        } else if (indexSubstract != -1) {
                            index = indexSubstract;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) - Double.parseDouble(param2);
                        } else if (indexAdd != -1) {
                            index = indexAdd;
                            param1 = listElements.get(index - 1);
                            param2 = listElements.get(index + 1);
                            result = Double.parseDouble(param1) + Double.parseDouble(param2);
                        }
                        countOperation++;
                        System.out.println("         после арифм операции countOperation "+countOperation+" result: "+result);

//                        System.out.println("230 после всех  операций  newIntoBracket : "+newIntoBracket +  " result: "+result);
//                        newIntoBracket = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
//                        newIntoBracket = result;
                        newIntoBracket = df.format(result);
                        listElements.set(index, newIntoBracket);
                        listElements.remove(index + 1);
                        listElements.remove(index - 1);
                        System.out.println("246 в  конце цикла:      счетчик ("+k+") list(" + listElements.size() +") "+ listElements.toString()+ "\n\n\n");
                    }
                    System.out.println("         после арифм операции countOperation "+countOperation+" result: "+result);
                    System.out.println("239 ==================== выход из цикла =======================");
                }
                System.out.println("251                      list(" + listElements.size() +") "+ listElements.toString()+ "");
                if(bracketExist&&!function){                                                           // усли существовали скобки
                    newExpression = new StringBuilder(expression).replace(bracketOpen - 1, bracketClose + 1, newIntoBracket).toString();
                    System.out.println("240 старое выражение : " + expression.substring(bracketOpen - 1, bracketClose + 1));
                    System.out.println("    новое  выражение : "+newIntoBracket.toString());
                }else{


 //                   newExpression = new StringBuilder(expression).replace(bracketOpen, bracketClose+1 , newIntoBracket).toString();
                    newExpression = new StringBuilder(expression).replace(bracketOpen, bracketClose , newIntoBracket).toString();




                    System.out.println("243 ("+bracketOpen +" " +bracketClose +") "+ listElements.toString()+ " передает выражение: "+
                            newExpression+ " newIntoBracket: "+newIntoBracket+"\n");
//                    System.out.println("242 старое выражение: " + expression.substring(bracketOpen, bracketClose));
                }
                System.out.println("\n\n");
                recurse(newExpression, countOperation);

            } else {                                                                          // если внутри скобок аргумент - функции
                System.out.println("259              до операции function countOperation "+countOperation+" result: "+result);
                if (indexSin != -1) {
                    result = Math.sin(Math.toRadians(Double.parseDouble(listElements.get(0))));
                } else if (indexCos != -1) {
                    result = Math.cos(Math.toRadians(Double.parseDouble(listElements.get(0))));
                } else if (indexTan != -1) {
                    result = Math.tan(Math.toRadians(Double.parseDouble(listElements.get(0))));
                }
                function = false;
                countOperation++;
                System.out.println("268           после операции function countOperation "+countOperation+" result: "+result);
                newIntoBracket = df.format(result);
                System.out.println("272: " + newIntoBracket);

                System.out.println();

                newExpression = new StringBuilder(expression).replace(bracketOpenFunction - 1, bracketClose + 1, newIntoBracket).toString();
                recurse(newExpression, countOperation);
            }
        }
    }



    public Solution() {
        //don't delete
    }
}*/

/*  возведение в степень
Double v = Math.pow(Double.parseDouble(param1), Double.parseDouble(param2));
String value = decimalFormat.format(v);
 */


/*
Принцип такой:
- в функцию приходит строка выражения и количество уже выполненных операций;
- проверяем, не является ли выражение обычным числом:
-- если является, то это и есть наш ответ. Просто выводим на экран его (с нужным форматированием) и количество сделанных операций.
-- если не является - находим в нем самое "глубокое" вложенное выражение (т. е. операцию, имеющую сейчас максимальный приоритет),
выполняем его, заменяем соответствующую ему подстроку на результат вычислений и в самом конце вызываем снова наш метод,
передавая в него новую строку и увеличенный на 1 счетчик операций. В результате возврат значения из метода никак не нужен.


1. Убрать все пробелы
2. Если в начале строки или после "(" или "+" или "-" идет цифра со знаком "-" переписываем как "0-цифра" (В помощь  (?<=^|[\(\+\-])-\d+\.?\d* )
3. Приоритет простейших операций: ^ / * - +
4. Чтобы найти одну простейшую операцию -?\d+\.?\d*\%s-?\d+\.?\d* где %s знак операции (например *)
5. Чтобы найти функцию в которой завершенные скобки (скобки в которых выполнены все простейшие операции) например cos(-30)
используем (sin|cos|tan)\((-?\d+\.?\d*\))
6. Чтобы найти все завершенные скобки, которые без функций (например 5+(10)-2 ищем (10) ), используем (?<!\w)\((-?\d+\.?\d*\))
7. После раскрытия завершенных скобок могут быть "артефакты" такие как "+-" и "--", которые нужно заменить на "-"
8. Самое главное, что метод recursion выполняет только простейшие операции (например 1+2, 5-8, cos(1) и т.д.).
Таким образом после первой итерации для выражения sin(2*(-5+1.5*4)+28) получим выражение sin(2*1+28)
и счётчик дейстивий равный 3. Новое выражение и счётчик отправляем на следующию итерацию в метод recursion("sin(2*1+28)", 3)
9. Округление new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
10. Вывод на экран (у меня -1 одна попытка):
NumberFormat format = new DecimalFormat("#.##");
System.out.println(String.format("%s %d", format.format(expressionResult),countOperation));
 */


/*result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
Ответить


Для вывода результата, пришлось гуглить решение на StackOverFlow, внезапно оно помогло
https://stackoverflow.com/questions/703396/how-to-nicely-format-floating-numbers-to-string-without-u...

/*
        //-- clear spaces and lowercase
        //-- clear outer brackets
        //-- count brackets and check count
        //-- split expression into 3 blocks
            //find left bracket position
            //find right bracket position
        //-- split middle block into operations list
        //-- process minus numbers
        //-- convert unary operators
        //-- process operators list
                //-- find operator to perform
                //-- perform operator
                //-- clear empty entries
        //-- check if all operations processed
                //-- print result and say goodbye
        //-- combine expression to process further

 */



/*
Нужно идти во внутренние скобки и заменять их решением и далее передавать в наш метод для следующего шага.
Помогли комментарии ниже курсанта Николай Соколов:
- удаляем внешние скобки, если они есть
- проверяем регуляркой может быть уже получили число, округляем до 2 знаков, удаляем нули в конце строки, выводим, выходим из рекурсии
- заходим во внутренние скобки (Подсказка: начинай с самой последней '(' )
//** - разбивал строку на токены с разделителями "+-*/  //^" (вспоминаем класс StringTokenizer)
/*
        - я сразу считал всё выражение в циклах, считая операции (приоритет такой ^ / * - + )
        - проверял есть ли перед скобкой функция, вычислял, окрулял полученное число до 2 знаков
        - если после раскрытия скобок в строке были '- -' или '+ -' менял на '-'
        - отдаем строку опять в рекурсию.

Ура 415!!! Посчитать countOperation через рекурсию так и не удалось((( То +1 то -1.
Посчитал в начале метода количество операций сразу...

Новый набор выражений для проверки https://javarush.ru/help/1302

new BigDecimal(result).setScale(2, RoundingMode.HALF_UP)
List of tests: https://javarush.ru/help/1302
Округлял после каждого вычисления. 2 раза не приняло, но когда убрал округление после возведения в степень, то прошло. Ответ тоже округлял.
Короче, просто война с валидатором.

я взял эту задачу !!!!
Нужно читать обсуждение http://info.javarush.ru/JavaRush_tasks_discussion/2015/01/13/level34-lesson02-home01.html

Еще мне помогла замена в вычислении степени с
BigDecimal v = new BigDecimal(param1).pow(new BigDecimal(param2).intValue(), MathContext.DECIMAL32);
String value = v.setScale(4, RoundingMode.HALF_UP).toString();
на
Double v = Math.pow(Double.parseDouble(param1), Double.parseDouble(param2));
String value = decimalFormat.format(v);

Очень помогли тесты http://info.javarush.ru/JavaRush_tasks_discussion/2015/01/13/level34-lesson02-home01.html#comment50848
Для тех, кто не знает, как подойти к задаче - попробуйте сначала обработать простой случай - когда всего один оператор.
А дальше добавляется разбиение на такие простые случаи и рекурсия в конце.
Решал без использования BigInteger, использовал Double + DecimalFormat("#.#######") для промежуточных вычислений и DecimalFormat("#.##") для итога.


Рекурсия для мат. выражения
На вход подается строка - математическое выражение.
Выражение включает целые и дробные числа, скобки (), пробелы, знак отрицания -, возведение в степень ^, sin(x), cos(x), tan(x)
Для sin(x), cos(x), tan(x) выражение внутри скобок считать градусами, например, cos(3 + 19*3)=0.5
Степень задается так: a^(1+3) и так a^4, что эквивалентно a*a*a*a.
С помощью рекурсии вычислить выражение и количество математических операций. Вывести через пробел результат в консоль.
Результат выводить с точностью до двух знаков, для 0.33333 вывести 0.33, использовать стандартный принцип округления.
Знак отрицания перед числом также считать математической операцией.
Не создавай в классе Solution дополнительные поля.
Не пиши косвенную рекурсию.

Пример, состоящий из операций sin * - + * +:
sin(2*(-5+1.5*4)+28)
Результат:
0.5 6

Пример, состоящий из операций tan ^:
tan(2025 ^ 0.5)
Результат:
1 2


Требования:
1. В классе Solution не должны быть созданы дополнительные поля.
2. Метод recurse должен выводить на экран результат вычисления заданного выражения (пример в условии).
3. Метод recurse не должен быть статическим.
4. Метод recurse должен быть рекурсивным.


                //               System.out.println("выражение"+ " "+ intoBracket);
                StringTokenizer st1 = new StringTokenizer(intoBracket, "0123456789.");
                int operations = st1.countTokens();                                 // количество операций внутри скобок
                System.out.println("стр 61 количество операций: "+ operations);


p 47                  int elements = st2.countTokens();               // количество операций и элементов внутри скобок
                System.out.println("стр 75 количество операций и элементов внутри скобок: "+ elements);

 */

/*
                                Double t = -Double.parseDouble(listElements.get(i + 1));         // change sign
                                listElements.set(i + 1, t.toString());
                                    //("^[-?\\(?]+[0-9]+([,.][0-9]+)?(\\))?"); -(-202)
    //("^-?\\(*[0-9]+([,.][0-9]+)?(\\))*"); (-202)

*/