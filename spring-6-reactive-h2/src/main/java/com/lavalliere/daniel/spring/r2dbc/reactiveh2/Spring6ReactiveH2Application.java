package com.lavalliere.daniel.spring.r2dbc.reactiveh2;

import com.lavalliere.daniel.spring.r2dbc.reactiveh2.mappers.BeerMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// @Import({BeerMapperImpl.class})
public class Spring6ReactiveH2Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring6ReactiveH2Application.class, args);
	}

}
