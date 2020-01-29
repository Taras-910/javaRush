package com.javarush.task.task33.task3301;

/*
Первая сериализация в JSON
*/
/*
public class Solution {
    public static void main(String[] args) throws IOException {
        Cat cat = new Cat();
        cat.name = "Murka";
        cat.age = 5;
        cat.weight = 3;

        Dog dog = new Dog();
        dog.name = "Killer";
        dog.age = 8;
        dog.owner = "Bill Jeferson";

        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(cat);
        pets.add(dog);

        StringWriter writer = new StringWriter();
        convertToJSON(writer, pets);
        System.out.println(writer.toString());
        //[{"name":"Murka","age":5,"weight":3},{"name":"Killer","age":8,"owner":"Bill Jeferson"}]
    }

    public static void convertToJSON(StringWriter writer, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, object);
    }
    @JsonAutoDetect
    public static class Pet {
       public String name;
    }
    @JsonAutoDetect
    public static class Cat extends Pet {
        public int age;
        public int weight;
    }
    @JsonAutoDetect
    public static class Dog extends Pet {
        public int age;
        public String owner;
    }
}
*/

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Сергей",38));
        persons.add(new Person("Даша",7));
        persons.add(new Person("Саша",3));
        persons.add(new Person("Глаша",6));
        persons.add(new Person("Анна",18));

/*
        for(Person p: nums){
            System.out.println(p);
*/
// пишется не набор команд - а результат, который мы хотим получить
// такие действия описываем декларативно над элементами коллекции
//         stream() возвращает нам новый объект класса Stream, у которого есть методы,
//         которые в качастве параметров принимают функциональные интерфейсы
//         Т.е. можно на их место подставить лямбда выражения(функции)
//        и эти функции будут выполняться для каждого элемента из массива

//        persons.stream().forEach(p -> System.out.println(p));


 /*       persons.stream().filter(p ->{
            return p.getAge() >= 18;
        }
        ).forEach(System.out::println);
*/

        persons.stream()
                .filter(p -> p.getAge() >= 18)
                .sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
                .map(p -> p.getName())
//                .forEach((String name) -> System.out.println(name));
                .forEach(System.out::println);

//---------------------------------------------------------------------
// 1
        int sum = 0;
        int adultPersons = 0;
        for(Person p: persons){
            if(p.getAge() >= 18){
                sum += p.getAge();
                adultPersons++;
            }
        }
        double averageAge1 = (double) sum/adultPersons;
        System.out.println(averageAge1);
// 2
        double averageAge2 = persons.stream()
                .filter(p -> p.getAge() >= 18)
                .mapToInt(p -> p.getAge())
                // среднее значение:
                .average()
                .getAsDouble();
        System.out.println(averageAge2);

    }
}
