package com.javarush.task.task36.task3606;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() {
        MyClassLoader loader = new MyClassLoader();
        File file = new File(packageName);
        for (File f : file.listFiles()) {
            Class clazz = loader.findClass(f.toString());
            hiddenClasses.add(clazz);
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        for (Class clazz : hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                try {
                    Constructor constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return (HiddenClass) constructor.newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

/*
Известно, что каждый класс имеет конструктор без параметров и реализует интерфейс HiddenClass.
Считай все классы с файловой системы, создай фабрику - реализуй метод getHiddenClassObjectByKey.
Примечание: в пакете может быть только один класс, простое имя которого начинается с String key без учета регистра.


Требования:
1. Реализуй метод scanFileSystem, он должен добавлять в поле hiddenClasses найденные классы.
2. Реализуй метод getHiddenClassObjectByKey, он должен создавать объект класса согласно условию задачи.
3. Метод main не изменяй.
4. Метод getHiddenClassObjectByKey не должен кидать исключений.
 */


/*
 constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
  */

/*
   public HiddenClass getHiddenClassObjectByKey(String key)  {
        Constructor constructor = null;
        for(Class clazz : hiddenClasses){
            if(!key.equals(clazz.getSimpleName().toLowerCase())) {
                continue;
            }
            else{
                try{
                    constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                }catch(NoSuchMethodException e){
                }
            }
            try {
                return (HiddenClass) constructor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
 */

/*

    public HiddenClass getHiddenClassObjectByKey(String key) {
        Constructor constructor = null;
//        System.out.println(hiddenClasses.toString());
int i = 0;
        for (Class clazz : hiddenClasses) {
            if (key.equals(clazz.getSimpleName().toLowerCase())) {


                try {
                    constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return (HiddenClass) constructor.newInstance();

                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            else{
                if (!key.toString().equals("secondhiddenclassimpl")&&HiddenClass.class.isAssignableFrom(clazz)) {

                    System.out.println("key: "+key+" clazz: "+clazz.getSimpleName().toLowerCase());

                    try {
                        constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        return (HiddenClass) constructor.newInstance();
                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        System.out.println("имя класса не совпадает"); }
                }
            }
        }
        System.out.println(i);
        return null;
    }

 */