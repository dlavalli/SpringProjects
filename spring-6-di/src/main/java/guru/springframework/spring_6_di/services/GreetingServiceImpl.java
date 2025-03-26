package guru.springframework.spring_6_di.services;

import org.springframework.stereotype.Service;

@Service("greetingServiceImpl")
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayGreetings() {
        return "Hello Everyone From Base Service!!!";
    }
}
