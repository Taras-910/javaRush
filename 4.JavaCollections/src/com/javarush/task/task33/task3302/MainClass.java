package com.javarush.task.task33.task3302;

public class MainClass {
    public static void main(String[] args) {

        int sum5 = sum(5);
        System.out.println(sum5);

        int sum7 = sum(7);
        System.out.println(sum7);
    }

    public static int sum(int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        return sum;
    }


}



/*

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;


// Вторая сериализация в JSON

public class Solution {
    public static void main(String[] args) throws IOException {
        Cat cat = new Cat();
        cat.name = "Murka";
        cat.age = 5;
        cat.weight = 3;

        StringWriter writer = new StringWriter();
        convertToJSON(writer, cat);
        System.out.println(writer.toString());
        //{"wildAnimal":"Murka","over":3}
    }

    public static void convertToJSON(StringWriter writer, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, object);
    }

    @JsonAutoDetect
    public static class Cat {
        @JsonProperty("wildAnimal")
        public String name;
        @JsonIgnore
        public int age;
        @JsonProperty("over")
        public int weight;

        Cat() {
        }
    }
}
*/
