package com.lavalliere.daniel.spring.reactive_mongo.mappers;

import com.lavalliere.daniel.spring.reactive_mongo.domain.Beer;
import com.lavalliere.daniel.spring.reactive_mongo.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    BeerDTO beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDTO beerDTO);
}