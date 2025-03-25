package guru.springframework.sfgdi.services;

import org.springframework.stereotype.Service;

// @Service
// Refactored to use instead guru.springframework.sfgdi.config.GreetingServiceConfig
// Usually, if you do not own the code (ie using thirdparty), you will usually use java base config
// (ie: config class) while when you own the code you will use the annotation based
public class PropertyInjectedGreetingService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello World - Property";
    }
}
