package com.lavalliere.daniel.ConsoleAppDemo1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

/*
 *
 * Other possibility is to have a bean factory to generate the runner
 *
 *

With a bean definition like

@Bean
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
protected PictureBean createPictureBean(Picture picture) {
    PictureBean bean = new PictureBean();
    bean.setPicture(picture);
    return bean;
}

the bean definition name is createPictureBean. You can invoke it every time using BeanFactory#getBean(String, Object...) like so

ApplicationContext ctx = ...; // instantiate the AnnotationConfigApplicationContext
Picture picture = ...; // get a Picture instance
PictureBean pictureBean = (PictureBean) ctx.getBean("createPictureBean", picture);

Spring will use the arguments given (picture in this case) to invoke the @Bean method.

If you weren't providing arguments, Spring would try to autowire arguments when invoking the method
but would fail because there was no Picture bean in the context.
* So possibly pass a bean argument or @Value("${...}") initialized variable

*
*
* An example using a bean factory to create the CommandLineRunner which is automatically executed after the application if sully loaded

@SpringBootApplication
public class ConsoleAppDemo1Application {

	@Bean
	public CommandLineRunner run() {
		return args -> {
		    // Print the count then exit
			for(int i=1; i < 100; i++) {
				System.out.println("Counting: " + i);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsoleAppDemo1Application.class, args);
	}
}
*/