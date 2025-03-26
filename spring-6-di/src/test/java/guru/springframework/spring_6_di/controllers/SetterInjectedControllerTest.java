package guru.springframework.spring_6_di.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class SetterInjectedControllerTest {

    @Autowired
    private SetterInjectedController controller;

    @Test
    void sayHello() {
        System.out.println(controller.sayHello());
    }
}