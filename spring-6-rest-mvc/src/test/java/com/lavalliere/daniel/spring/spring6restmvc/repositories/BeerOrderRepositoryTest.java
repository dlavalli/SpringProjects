package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.bootstrap.BootstrapData;
import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.domain.BeerOrder;
import com.lavalliere.daniel.spring.spring6restmvc.domain.BeerOrderShipment;
import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import com.lavalliere.daniel.spring.spring6restmvc.services.BeerCsvServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest  // Need complete application context so test splice won't do (will fail on autowired)
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().getFirst();
        testBeer = beerRepository.findAll().getFirst();
    }

    @Transactional
    @Test
    void testBeerOrders() {
        BeerOrder beerOrder = BeerOrder.builder()
            .customerRef("Test Order")
            .customer(testCustomer)
            .beerOrderShipment(BeerOrderShipment.builder()
                .trackingNumber("1235r")
                .build())
            .build();

        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

        System.out.println(savedBeerOrder.getCustomerRef());
    }
}