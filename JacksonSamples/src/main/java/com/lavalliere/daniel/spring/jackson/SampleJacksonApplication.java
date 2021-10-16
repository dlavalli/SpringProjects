package com.lavalliere.daniel.spring.jackson;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.function.Predicate.not;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
class FootballPlayer {
    // Jackson rules: your fields need to be public
    // or they need to have public getter / setters
    private String name;
    private int number;
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
class Car {
    // Jackson rules: your fields need to be public
    // or they need to have public getter / setters

    @JsonAlias({ "brandName", "brand_name", "myBrandName" })
    private String brand;

    @JsonAlias({ "type" })
    private String model;
    private String color;
}


public class SampleJacksonApplication {


    public static void main(String[] args) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        FootballPlayer footballPlayer1 = new FootballPlayer("Joe", 10);

        // Serialize JSON to a String example
        String jsonString = mapper.writeValueAsString(footballPlayer1);
        System.out.println(jsonString);

        //Serialize JSON to a file
        // Example file name C:\Users\dlava\AppData\Local\Temp\16106384687161465188.json
        Path file1path = Files.createTempFile(null,".json");
        File file1 = file1path.toFile();
        mapper.writeValue(file1, footballPlayer1);
        Files.lines(file1path)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .forEach(System.out::println);

        //  Deserialize from a string
        FootballPlayer footballPlayer2 = mapper.readValue(jsonString.replaceAll("\"","\\\""), FootballPlayer.class);
        System.out.println(String.format("Object serialize/deserialized are equal: %s\n", footballPlayer2.equals(footballPlayer1) ? "true" : "false"));

        // Deserailaize from a file
        footballPlayer2 = mapper.readValue(file1, FootballPlayer.class);
        Files.lines(file1path)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .forEach(System.out::println);
        Files.deleteIfExists(file1path);

        // Serialization Java List to JSON String
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("milk");
        shoppingList.add("oranges");
        shoppingList.add("light bulbs");
        jsonString = mapper.writeValueAsString(shoppingList);
        System.out.println(jsonString);

        // Serialize JJav MAP to JSON String
        Map<String, String> capitalsMap = new HashMap<>();
        capitalsMap.put("France", "Paris");
        capitalsMap.put("Spain", "Madrid");
        capitalsMap.put("Japan", "Tokyo");
        jsonString = mapper.writeValueAsString(capitalsMap);
        System.out.println(jsonString);


        // Jackson Annotations  @JsonAlias: de-serialization - Non matching field names between JSON and POJO
        jsonString = "{ \"brandName\" : \"Honda\", \"type\" : \"Accord\", \"color\" : \"Red\"}";
        Car car = mapper.readValue(jsonString, Car.class);
        System.out.println(String.format("Car brandName: %s Type: %s Color:%s\n", car.getBrand(), car.getModel(), car.getColor()));

        // Jackson Annotations  @JsonIgnore: de-serialization -
    }
}
