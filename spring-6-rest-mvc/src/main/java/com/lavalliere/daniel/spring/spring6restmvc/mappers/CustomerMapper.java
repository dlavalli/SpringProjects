package com.lavalliere.daniel.spring.spring6restmvc.mappers;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import com.lavalliere.daniel.spring.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDto(Customer customer);
}
