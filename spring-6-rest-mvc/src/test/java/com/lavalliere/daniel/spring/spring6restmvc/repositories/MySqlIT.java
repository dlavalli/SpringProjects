package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles({"localmysql"})
@SpringBootTest  // Full context for integration text
public class MySqlIT {  // Mark it as an integration test
    // surefire will run with the test goal
    // failsafe will run with the verify goal


    // With Service Connections / Dynamic Properties
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.1.0");


    /* Without Service Connection / Dynamic properties
    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.1.0");  // The image version requested: mysql:9
        // .withCommand("mysqld", "--innodb-log-file-size=5M");

    // Retrieve the container's dynamic properties
    // https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-dynamicpropertysource.html
    // https://www.baeldung.com/spring-dynamicpropertysource
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/DynamicPropertyRegistry.html
    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username",mySQLContainer::getUsername);
        registry.add("spring.datasource.password",mySQLContainer::getPassword);
        registry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
    }
     */

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(0);
    }
}
