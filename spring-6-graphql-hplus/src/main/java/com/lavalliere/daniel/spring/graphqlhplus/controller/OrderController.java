package com.lavalliere.daniel.spring.graphqlhplus.controller;

import com.lavalliere.daniel.spring.graphqlhplus.model.Order;
import com.lavalliere.daniel.spring.graphqlhplus.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderRepository orderRepository;

    @QueryMapping
    public Iterable<Order> orders(){
        return this.orderRepository.findAll();
    }

    @QueryMapping
    public Order orderById(@Argument String id){
        return this.orderRepository.findById(id).orElseThrow();
    }
}
