package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring5MongoRecipeAppApplication {

	// See https://stackoverflow.com/questions/20437405/spring-data-mongodb-converter-error-java-lang-stackoverflowerror
	// for infinite loop info

	public static void main(String[] args) {
		SpringApplication.run(Spring5MongoRecipeAppApplication.class, args);
	}
}
