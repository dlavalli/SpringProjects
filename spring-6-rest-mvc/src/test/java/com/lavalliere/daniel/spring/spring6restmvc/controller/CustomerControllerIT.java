package com.lavalliere.daniel.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.BeerMapperImpl;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapper;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapperImpl;
import com.lavalliere.daniel.spring.spring6restmvc.model.CustomerDTO;
import com.lavalliere.daniel.spring.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.lavalliere.daniel.spring.spring6restmvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;

@SpringBootTest  // Load full context not a test splice
@Import({BeerMapperImpl.class, CustomerMapperImpl.class})
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .apply(springSecurity())
            .build();
    }


    @Test
    void testPatchCustomerBadname() throws Exception {
        Customer customer = customerRepository.findAll().getFirst();

        // Reflects the field(s) that would be supplied by the api endpoint caller
        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "New Name 012345678900123456789001234567890012345678900123456789001234567890");

        // Can replace patch(CustomerController.CUSTOMER_PATH+"/"+customer.getId()  by the version below
        // since the patch method has a version that takes the host/path with a variable arguments
        MvcResult result = mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID,customer.getId() )
                .with(jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerMap))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.length()", is(1)))
            .andReturn();
    }

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