package guru.springframework.spring_6_di.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest  // @NO Autowired   without this annotation
class MyControllerTest {


    @Autowired
    private MyController controller;

    @Test
    void sayHello() {
        System.out.println(controller.sayHello());
    }
}