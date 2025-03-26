package guru.springframework.spring_6_di.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class GreetingServicePrimary implements GreetingService {

    @Override
    public String sayGreetings() {
        return "Hello Everyone From Primary!!!";
    }
}
