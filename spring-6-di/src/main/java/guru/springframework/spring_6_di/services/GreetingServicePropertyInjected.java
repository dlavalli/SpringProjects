package guru.springframework.spring_6_di.services;

import org.springframework.stereotype.Service;

@Service("greetingServicePropertyInjected")
public class GreetingServicePropertyInjected implements GreetingService {
    @Override
    public String sayGreetings() {
        return "Hello Everyone From Property Injected Bean!!!";
    }
}
