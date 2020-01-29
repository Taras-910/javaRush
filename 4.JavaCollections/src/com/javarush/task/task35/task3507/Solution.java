package com.javarush.task.task35.task3507;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
              Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println("\n"+allAnimals);
    }
    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        List result = new ArrayList();
        File file = new File(pathToAnimals);
        List<String>list = new ArrayList();

        for(File f : file.listFiles()) {                 // names of files
            list.add(f.toString());
        }
        MyClassLoader loader = new MyClassLoader();
            for (String f : list) {
            Class clazz = loader.findClass(f);
            System.out.println("clazz наследует Animal?: "+Animal.class.isAssignableFrom(clazz));
            if(!Animal.class.isAssignableFrom(clazz)) continue;
            Constructor constructor = null;
            try{
                    constructor = clazz.getConstructor();
            }catch(Exception e){
                System.out.println("элемент не имеет публичного конструктора");
                continue;
            }
            result.add(constructor.newInstance().getClass());
            }
            return new HashSet<Animal>(result);
    }
}



/*
Размер множества возвращаемого методом getAllAnimals должен быть равен количеству классов поддерживающих интерфейс Animal
и имеющих публичный конструктор без параметров (среди классов расположенных в директории переданной в качестве параметра).
В множестве возвращаемом методом getAllAnimals должны присутствовать все классы поддерживающие интерфейс Animal
и имеющие публичный конструктор без параметров (среди классов расположенных в директории переданной в качестве параметра).

ClassLoader - что это такое?
Реализуй логику метода getAllAnimals.
Аргумент метода pathToAnimals - это абсолютный путь к директории, в которой хранятся скомпилированные классы.
Путь не обязательно содержит / в конце.
НЕ все классы наследуются от интерфейса Animal.
НЕ все классы имеют публичный конструктор без параметров.
Только для классов, которые наследуются от Animal и имеют публичный конструктор без параметров, - создать по одному объекту.
Добавить созданные объекты в результирующий сет и вернуть.
Метод main не участвует в тестировании.

Требования:
1. Размер множества возвращаемого методом getAllAnimals должен быть равен количеству классов поддерживающих интерфейс Animal
и имеющих публичный конструктор без параметров (среди классов расположенных в директории переданной в качестве параметра).
2. В множестве возвращаемом методом getAllAnimals должны присутствовать все классы поддерживающие интерфейс Animal
и имеющие публичный конструктор без параметров (среди классов расположенных в директории переданной в качестве параметра).
3. В множестве возвращаемом методом getAllAnimals НЕ должен присутствовать ни один класс не поддерживающий интерфейс Animal
или не имеющий публичного конструктора без параметров (среди классов расположенных в директории переданной в качестве параметра).
4. Метод getAllAnimals должен быть статическим.
 */