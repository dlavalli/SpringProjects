package com.lavalliere.daniel.spring.spring6restmvc.controller;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapper;
import com.lavalliere.daniel.spring.spring6restmvc.model.BeerDTO;
import com.lavalliere.daniel.spring.spring6restmvc.model.CustomerDTO;
import com.lavalliere.daniel.spring.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Load full context not a test splice
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteCustomerById() {
        Customer existingCustomer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(existingCustomer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(existingCustomer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCustomer() {
        Customer existingCustomer = customerRepository.findAll().get(0);
        CustomerDTO customerDto = customerMapper.customerToCustomerDto(existingCustomer);
        customerDto.setId(null);
        customerDto.setVersion(null);
        final String name = "UPDATED";
        customerDto.setName(name);

        ResponseEntity responseEntity = customerController.updateCustomerById(existingCustomer.getId(), customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updatedCustomer = customerRepository.findById(existingCustomer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(name);
    }

    @Test
    @Transactional
    @Rollback
    void testSaveNewCustomer() {
        CustomerDTO customerDto = CustomerDTO.builder()
            .name("New Beer")
            .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());  // Throws
        });
    }

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListCustomer() {
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testListEmpty() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}