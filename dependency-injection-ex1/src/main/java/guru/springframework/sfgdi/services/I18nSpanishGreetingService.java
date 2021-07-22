package guru.springframework.sfgdi.services;

import guru.springframework.sfgdi.repositories.EnglishGreetingRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Profile("ES")
//@Service("i18nService")
// Refactored to use instead guru.springframework.sfgdi.config.GreetingServiceConfig
// Usually, if you do not own the code (ie using thirdparty), you will usually use java base config
// (ie: config class) while when you own the code you will use the annotation based
public class I18nSpanishGreetingService implements guru.springframework.sfgdi.services.GreetingService {
    @Override
    public String sayGreeting() {
        return "Hola Mundo - ES";
    }
}
