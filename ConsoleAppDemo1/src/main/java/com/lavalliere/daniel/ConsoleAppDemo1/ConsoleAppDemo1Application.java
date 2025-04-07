package com.lavalliere.daniel.ConsoleAppDemo1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ConsoleAppDemo1Application implements CommandLineRunner {

	// https://www.youtube.com/watch?v=PXwSOU-ECXs&t=518s
	@Override
	public void run(String... args) throws Exception {

		String result = "";
		String gesture = "";
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("What will it be ? Rock, Paper or Scissors: ");
			gesture = scanner.next();
			switch (gesture.toUpperCase()) {
				case "PAPER":
					result = "win";
					break;
				case "ROCK":
					result = "tie";
					break;
				case "SCISSORS":
				default:
					result = "loss";
					break;

			}
			System.out.println("You picked: " + gesture);
			System.out.println("The result is a: " + result);
		} while(!gesture.equalsIgnoreCase("q"));
		scanner.close();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsoleAppDemo1Application.class, args);
	}
}