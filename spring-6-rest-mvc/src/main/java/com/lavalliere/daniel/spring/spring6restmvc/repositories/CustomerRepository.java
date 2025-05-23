package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
