package com.lavalliere.daniel.spring.graphqlhplus.controller;

import com.lavalliere.daniel.spring.graphqlhplus.model.Customer;
import com.lavalliere.daniel.spring.graphqlhplus.model.CustomerInput;
import com.lavalliere.daniel.spring.graphqlhplus.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;

    @QueryMapping
    public Iterable<Customer> customers() {
        return customerRepository.findAll();
    }

    @QueryMapping
    public Customer customerById(@Argument Long id) {  // id must ba actual name for that field else need to override in QueryMapping
        return customerRepository.findById(id).orElseThrow(); // Jpa method returns an optional so handle invalid case
    }

    @QueryMapping
    public Customer customerByEmail(@Argument String email) { // email must ba actual name for that field else need to override in QueryMapping
        return customerRepository.findCustomerByEmail(email);
    }

    @MutationMapping
    public Customer addCustomer(@Argument(name="input") CustomerInput customerInput) {
        return customerRepository.save(customerInput.getEntity());
    }
}
