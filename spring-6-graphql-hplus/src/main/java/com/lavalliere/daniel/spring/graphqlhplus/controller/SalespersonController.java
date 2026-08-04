package com.lavalliere.daniel.spring.graphqlhplus.controller;

import com.lavalliere.daniel.spring.graphqlhplus.model.Salesperson;
import com.lavalliere.daniel.spring.graphqlhplus.repository.SalespersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class SalespersonController {
    private final SalespersonRepository salespersonRepository;

    @QueryMapping
    public Iterable<Salesperson> salespeople(){
        return this.salespersonRepository.findAll();
    }

    @QueryMapping
    public Salesperson salespersonById(@Argument Long id){
        return this.salespersonRepository.findById(id).orElseThrow();
    }

    @QueryMapping
    public Salesperson salespersonByEmail(@Argument String email){
        return this.salespersonRepository.findSalespersonByEmail(email);
    }
}
