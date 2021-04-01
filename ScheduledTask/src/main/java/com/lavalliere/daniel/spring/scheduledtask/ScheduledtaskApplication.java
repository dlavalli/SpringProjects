package com.lavalliere.daniel.spring.scheduledtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduledtaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduledtaskApplication.class, args);
	}

}
