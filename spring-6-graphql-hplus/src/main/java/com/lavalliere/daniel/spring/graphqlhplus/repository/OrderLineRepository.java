package com.lavalliere.daniel.spring.graphqlhplus.repository;

import com.lavalliere.daniel.spring.graphqlhplus.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
