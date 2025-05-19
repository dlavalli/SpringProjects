package com.lavalliere.daniel.spring.r2dbc.reactiveh2.services;

import com.lavalliere.daniel.spring.r2dbc.reactiveh2.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Mono<BeerDTO> getBeerById(Integer beerId);
    Flux<BeerDTO> listBeers();
    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDTO);
    Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDTO);
    Mono<Void> deleteBeerById(Integer beerId);
}