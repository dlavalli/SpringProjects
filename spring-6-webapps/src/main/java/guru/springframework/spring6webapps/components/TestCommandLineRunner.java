package guru.springframework.spring6webapps.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component
public class TestCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        var defaultConversionService = new DefaultConversionService();
        var applicationCOnversionService = new ApplicationConversionService();
        defaultConversionService.toString().lines().skip(1).forEach(System.out::println);
        applicationCOnversionService.toString().lines().skip(1).forEach(System.out::println);
    }
}
