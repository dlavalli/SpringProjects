package guru.springframework.sfgdi.controllers;

import guru.springframework.sfgdi.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class ConstructorInjectedController {

    // SOLID principle, can extend behaviour but not change implementattion
    private final GreetingService greetingService;

    // @Autowired    // Optional since Spring 4.2

    // 1st possibility using @Primary bean annotated service
    // public ConstructorInjectedController(GreetingService greetingService)

    // 2nd possibility,m using Qualifier
    public ConstructorInjectedController(@Qualifier("constructorGreetingService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String getGreeting() {
        return greetingService.sayGreeting();
    }
}
