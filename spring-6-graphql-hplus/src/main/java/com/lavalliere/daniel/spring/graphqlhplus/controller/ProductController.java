package com.lavalliere.daniel.spring.graphqlhplus.controller;

import com.lavalliere.daniel.spring.graphqlhplus.model.Product;
import com.lavalliere.daniel.spring.graphqlhplus.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductRepository productRepository;

    @QueryMapping
    public Iterable<Product> products(){
        return this.productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id){
        return this.productRepository.findById(id).orElseThrow();
    }
}
