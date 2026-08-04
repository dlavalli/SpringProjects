package com.lavalliere.daniel.spring.graphqlhplus.repository;

import com.lavalliere.daniel.spring.graphqlhplus.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);
}
