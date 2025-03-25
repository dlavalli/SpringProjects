package guru.springframework.sfgdi.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Primary
//@Service
// Refactored to use instead guru.springframework.sfgdi.config.GreetingServiceConfig
// Usually, if you do not own the code (ie using thirdparty), you will usually use java base config
// (ie: config class) while when you own the code you will use the annotation based
public class PrimaryGreetingService implements guru.springframework.sfgdi.services.GreetingService  {
    @Override
    public String sayGreeting() {
        return "Hello World - from Primary Bean";
    }
}
