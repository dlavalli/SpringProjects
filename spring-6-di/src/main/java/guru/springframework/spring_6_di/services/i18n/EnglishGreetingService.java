package guru.springframework.spring_6_di.services.i18n;


import guru.springframework.spring_6_di.services.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("i18NService")
@Profile({"EN", "default"})
public class EnglishGreetingService implements GreetingService {

    @Override
    public String sayGreetings() {
        return "Hello World - EN";
    }
}
