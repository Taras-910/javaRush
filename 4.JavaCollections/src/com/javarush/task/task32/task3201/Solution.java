package com.javarush.task.task32.task3201;
import static java.lang.System.out;
/* 
Запись в существующий файл
*/
public class Solution {

    public static void fire(Object sender){
        out.println("Сообщение");
    }

    public static void main(String[] args) {
        Switcher sw = new Switcher();
        Lamp lamp = new Lamp();
        Radio radio = new Radio();


        //подписка на событие   event subscribe
        sw.addElectricityListener(lamp);    // заносим в поле нашего выключателя объект класса, ктр реализует наш интерфейс
        sw.addElectricityListener(radio);

/*  1-й вариант
        class Fire implements ElectricityCosumer{
            @Override
            public void electricityOn() {
                System.out.println("Fire");
            }
        }
        sw.addElectricityListener(new Fire());
*/

 //  2-й вариант через анонимный класс
        sw.addElectricityListener(
                new ElectricityCosumer() {
                    public void electricityOn(Object sender) {
                        out.print("Fire1");
                        out.println("!!!");
                    }
                }
        );

        sw.addElectricityListener(
                sender -> {
                    out.print("Fire1");
                }
        );


//   3-й вариант - использование в качестве параметра функцию ктр реализует ЕДИНСТВЕННЫЙ МЕТОД ФУНКЦИОНАЛЬНОГО ИНТЕРФЕЙСА
        sw.addElectricityListener(sender -> out.println("Fire2!!!"));

        sw.addElectricityListener(s -> Solution.fire(s));

        // если набор типов параметров вызываемого метода (fire ) совпадает с типами параметров интерфейса():
        // :: указывает что речь идет о методе а не о поле
        sw.addElectricityListener(Solution::fire);






        sw.switchOn();

    }
}

