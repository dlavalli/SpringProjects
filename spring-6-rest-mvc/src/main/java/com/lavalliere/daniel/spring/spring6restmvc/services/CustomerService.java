package com.lavalliere.daniel.spring.spring6restmvc.services;

import com.lavalliere.daniel.spring.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);
    Customer saveNewCustomer(Customer customer);
    void updateCustomerById(UUID customerId, Customer customer);
    void deleteCustomerById(UUID customerId);
    void patchCustomerById(UUID customerId, Customer customer);
}
