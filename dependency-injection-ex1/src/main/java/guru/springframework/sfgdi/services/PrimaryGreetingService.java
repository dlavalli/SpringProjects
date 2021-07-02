package guru.springframework.sfgdi.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PrimaryGreetingService implements guru.springframework.sfgdi.services.GreetingService  {
    @Override
    public String sayGreeting() {
        return "Hello World - from Primary Bean";
    }
}
