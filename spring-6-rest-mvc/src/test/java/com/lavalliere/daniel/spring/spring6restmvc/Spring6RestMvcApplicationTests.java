package com.lavalliere.daniel.spring.spring6restmvc;

import com.lavalliere.daniel.spring.spring6restmvc.mappers.BeerMapperImpl;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({BeerMapperImpl.class, CustomerMapperImpl.class})
class Spring6RestMvcApplicationTests {

	@Test
	void contextLoads() {
	}

}
