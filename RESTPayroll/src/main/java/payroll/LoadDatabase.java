package payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

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

    /*

    // En example on how we would connect to a real Mysql database
    // If we want to use JPA with MySQL database, then we need the mysql-connector-java dependency,

    // as well as to define the DataSource configuration.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("mysqluser");
        dataSource.setPassword("mysqlpass");
        dataSource.setUrl(
                "jdbc:mysql://localhost:3306/myDb?createDatabaseIfNotExist=true");

        return dataSource;
    }

    // OR To configure the data source using a properties file,
    // we have to set properties prefixed with spring.datasource:
    //      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    //      spring.datasource.username=mysqluser
    //      spring.datasource.password=mysqlpass
    //      spring.datasource.url=
    //      jdbc:mysql://localhost:3306/myDb?createDatabaseIfNotExist=true

    // Spring Boot will automatically configure a data source based on these properties.
    // Also in Spring Boot 1, the default connection pool was Tomcat, but with Spring Boot 2 it has been changed to HikariCP.

    */
}
