package guru.springframework.spring_6_di.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"EN","dev"})
class EnvironmentControllerTest {

    @Autowired
    EnvironmentController controller;

    @Test
    void getEnvironment() {
        assertNotNull(controller);
        System.out.println(controller.getEnvironment());
    }
}