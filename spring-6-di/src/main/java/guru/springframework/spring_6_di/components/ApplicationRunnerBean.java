package guru.springframework.spring_6_di.components;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationRunnerBean implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(args.getNonOptionArgs());
        System.out.println(args.getOptionNames());
        System.out.println(Arrays.toString(args.getSourceArgs()));
        for(String optionName: args.getOptionNames()) {
            System.out.println(args.getOptionValues(optionName));
        }
    }
}
