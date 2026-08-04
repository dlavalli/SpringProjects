package com.lavalliere.daniel.spring.graphqlhplus.repository;

import com.lavalliere.daniel.spring.graphqlhplus.model.Salesperson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalespersonRepository extends JpaRepository<Salesperson, Long> {
    Salesperson findSalespersonByEmail(String email);
}
