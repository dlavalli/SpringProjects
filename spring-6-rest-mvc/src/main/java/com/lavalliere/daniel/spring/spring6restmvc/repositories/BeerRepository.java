package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.model.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    // https://docs.spring.vmware.com/spring-data-jpa-distribution/docs/3.1.13/reference/html/index.html
    List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);
    List<Beer> findAllByBeerStyle(BeerStyle beerStyle);
    List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle);
}
