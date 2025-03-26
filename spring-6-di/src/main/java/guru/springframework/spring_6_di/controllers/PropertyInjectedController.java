package guru.springframework.spring_6_di.controllers;

import guru.springframework.spring_6_di.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyInjectedController {

    // Least preferred method
    @Qualifier("greetingServicePropertyInjected")
    @Autowired
    GreetingService greetingService;

    public String sayHello(){
        return greetingService.sayGreetings();
    }

}
