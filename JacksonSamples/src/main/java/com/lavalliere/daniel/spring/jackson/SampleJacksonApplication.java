package com.lavalliere.daniel.spring.jackson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public String name;
    public int number;
}

public class SampleJacksonApplication {


    public static void main(String[] args) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        FootballPlayer footballPlayer1 = new FootballPlayer("Joe", 10);

        // Serialize JSON to a String example
        String jsonString = mapper.writeValueAsString(footballPlayer1);
        System.out.println(jsonString);

        // Example C:\Users\dlava\AppData\Local\Temp\16106384687161465188.json
        Path file1path = Files.createTempFile(null,".json");
        File file1 = file1path.toFile();
        mapper.writeValue(file1, footballPlayer1);
        Files.lines(file1path)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .forEach(System.out::println);
        Files.deleteIfExists(file1path);

        //  Deserialize from a string
        FootballPlayer footballPlayer2 = mapper.readValue(jsonString.replaceAll("\"","\\\""), FootballPlayer.class);
        System.out.println(String.format("Object serialize/deserialized are equal: %s\n", footballPlayer2.equals(footballPlayer1) ? "true" : "false"));
    }
}
