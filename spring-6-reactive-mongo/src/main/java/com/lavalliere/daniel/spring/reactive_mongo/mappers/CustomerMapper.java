package com.lavalliere.daniel.spring.reactive_mongo.mappers;

import com.lavalliere.daniel.spring.reactive_mongo.domain.Customer;
import com.lavalliere.daniel.spring.reactive_mongo.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}