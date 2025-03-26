package guru.springframework.spring_6_di.components;

import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements org.springframework.boot.CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println(args.length);
        for(String arg: args) {
            System.out.println(arg);
        }
    }
}
