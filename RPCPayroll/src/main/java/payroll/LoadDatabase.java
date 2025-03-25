package payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Spring @Configuration annotation is part of the spring core framework.
// Spring Configuration annotation indicates that the class has @Bean definition methods.
// So Spring container can process the class and generate Spring Beans to be used in the application.
// Spring @Configuration annotation allows us to use annotations for dependency injection.



// What happens when it gets loaded?  Spring Boot will run ALL CommandLineRunner beans
// once the application context is loaded. This runner will request a copy of the EmployeeRepository
// you just created. Using it, it will create two entities and store them.

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    // Here's a configuration class supplying bean metadata to an IoC container:
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return (args) -> {
            // Logging result of inerting 2 records in the database
            // Since ID are auto generated, they are not part of the constructor
            // Will output:
            //      Preloading Employee{id=1, name='Bilbo Baggins', role='burglar'}
            //      Preloading Employee{id=2, name='Frodo Baggins', role='thief'}
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
        };
    }
}
