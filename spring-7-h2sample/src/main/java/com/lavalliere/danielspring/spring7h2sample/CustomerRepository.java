package com.lavalliere.danielspring.spring7h2sample;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {}
