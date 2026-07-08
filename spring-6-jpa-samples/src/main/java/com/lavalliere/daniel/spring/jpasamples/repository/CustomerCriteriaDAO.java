package com.lavalliere.daniel.spring.jpasamples.repository;

import com.lavalliere.daniel.spring.jpasamples.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerCriteriaDAO {
    List<Customer> findCustomersWithHighValueOrders(String tier, String value);
    List<Customer> findCustomersByTier(String tier);
    List<Customer> findCustomersWithOrdersAbove(BigDecimal minAmount);
}
