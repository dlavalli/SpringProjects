package com.lavalliere.daniel.spring.r2dbc.reactiveh2.repopsitories;

import com.lavalliere.daniel.spring.r2dbc.reactiveh2.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// Also possible
// import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
