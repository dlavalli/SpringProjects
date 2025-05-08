package com.lavalliere.daniel.spring.spring6restmvc;

import com.lavalliere.daniel.spring.spring6restmvc.mappers.BeerMapperImpl;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@EnableCaching
@SpringBootApplication
@Import({BeerMapperImpl.class, CustomerMapperImpl.class})
public class Spring6RestMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6RestMvcApplication.class, args);
	}

}
