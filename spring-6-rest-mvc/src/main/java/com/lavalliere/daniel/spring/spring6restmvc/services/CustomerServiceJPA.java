package com.lavalliere.daniel.spring.spring6restmvc.services;

import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapper;
import com.lavalliere.daniel.spring.spring6restmvc.model.CustomerDTO;
import com.lavalliere.daniel.spring.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(customerMapper::customerToCustomerDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(
            customerMapper.customerToCustomerDto(
                customerRepository.findById(id).orElse(null)
            )
        );
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDto(
            customerRepository.save(customerMapper.customerDtoToCustomer(customer))
        );
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customer.getName());
            atomicReference.set(Optional.of(
                customerMapper.customerToCustomerDto(
                    customerRepository.save(foundCustomer)
                )
            ));
            customerRepository.save(foundCustomer);
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getName())){
                foundCustomer.setName(customer.getName());
            }
            atomicReference.set(Optional.of(customerMapper
                .customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
