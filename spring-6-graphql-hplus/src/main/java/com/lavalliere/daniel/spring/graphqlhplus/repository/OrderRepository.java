package com.lavalliere.daniel.spring.graphqlhplus.repository;

import com.lavalliere.daniel.spring.graphqlhplus.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
