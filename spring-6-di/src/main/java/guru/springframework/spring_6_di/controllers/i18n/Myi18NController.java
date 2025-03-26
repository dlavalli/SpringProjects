package guru.springframework.spring_6_di.controllers.i18n;

import guru.springframework.spring_6_di.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class Myi18NController {

    private final GreetingService greetingService;

    public Myi18NController(
        @Qualifier("i18NService") GreetingService greetingService
    ) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        return greetingService.sayGreetings();
    }
}

