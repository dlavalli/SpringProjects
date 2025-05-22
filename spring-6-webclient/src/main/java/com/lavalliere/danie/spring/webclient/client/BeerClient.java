package com.lavalliere.danie.spring.webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.lavalliere.danie.spring.webclient.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

public interface BeerClient {
    Flux<String> listBeer();
    Flux<Map> listBeerMap();
    Flux<JsonNode> listBeersJsonNode();
    Flux<BeerDTO> listBeerDtos();
    Mono<BeerDTO> getBeerById(String id);
    Flux<BeerDTO> getBeerByBeerStyle(String beerStyle);
    Mono<BeerDTO> createBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);
    Mono<BeerDTO> patchBeer(BeerDTO beerDTO);
    Mono<Void> deleteBeer(String beerId);
}
