package guru.springframework.spring_6_di.controllers;

import guru.springframework.spring_6_di.services.GreetingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    private GreetingService greetingService;

    @Value("${basic.property.value: 'unknown'}")
    private String propertyValue;

    // Preferred method: Using contructor injection
    public MyController(
        @Qualifier("greetingServiceImpl") GreetingService greetingService
    ) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        System.out.println("I'm in the controller with property: "+ propertyValue);

        return greetingService.sayGreetings();
    }

    public void beforeInit(){
        System.out.println("## - Before Init - Called by Bean Post Processor");
    }

    public void afterInit(){
        System.out.println("## - After init called by Bean Post Processor");
    }
}
