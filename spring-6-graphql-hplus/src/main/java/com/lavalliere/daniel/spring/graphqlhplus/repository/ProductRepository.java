package com.lavalliere.daniel.spring.graphqlhplus.repository;

import com.lavalliere.daniel.spring.graphqlhplus.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
