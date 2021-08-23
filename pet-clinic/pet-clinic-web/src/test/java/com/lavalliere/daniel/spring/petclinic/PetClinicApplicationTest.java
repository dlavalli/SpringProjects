package com.lavalliere.daniel.spring.petclinic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)  // This is actually an Intergration tst and it loads the spring context
@SpringBootTest
class PetClinicApplicationTest {



	@BeforeEach
	public void setup() {
	}

	@AfterEach
	public void cleanup() {
	}

	@Test
	void contextLoads() {
	}
}
