package com.lavalliere.daniel.spring.restclient.client;


import com.lavalliere.daniel.spring.restclient.model.BeerDTO;
import com.lavalliere.daniel.spring.restclient.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BeerClient {
    Page<BeerDTO> listBeers();
    Page<BeerDTO> listBeers(
        String beerName,
        BeerStyle beerStyle,
        Boolean showInventory,
        Integer pageNumber,
        Integer pageSize
    );
    BeerDTO getBeerById(UUID beerId);
    BeerDTO createBeer(BeerDTO newDto);
    BeerDTO updateBeer(BeerDTO beerDto);
    void deleteBeer(UUID beerId);
}

