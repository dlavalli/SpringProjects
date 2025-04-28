package com.lavalliere.daniel.spring.spring6restmvc.bootstrap;

import com.lavalliere.daniel.spring.spring6restmvc.repositories.BeerRepository;
import com.lavalliere.daniel.spring.spring6restmvc.repositories.CustomerRepository;
import com.lavalliere.daniel.spring.spring6restmvc.services.BeerCsvService;
import com.lavalliere.daniel.spring.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BeerCsvServiceImpl.class})  // required because we are using a testsplice and that bean is NOT autoloaded
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService csvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository, csvService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}