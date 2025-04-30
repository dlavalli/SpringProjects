package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.domain.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
