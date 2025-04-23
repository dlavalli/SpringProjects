package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Customer savedCustomer = customerRepository.save(Customer.builder()
                .name("My Beer 0123345678901233456789012334567890123345678901233456789012334567890123345678901233456789")

                .build());

            customerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Customer savedCustomer = customerRepository.save(
            Customer.builder()
                .name("My Name")
                .build()
        );

        customerRepository.flush(); // Required to make sure all save done before doe the validation below

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }
}