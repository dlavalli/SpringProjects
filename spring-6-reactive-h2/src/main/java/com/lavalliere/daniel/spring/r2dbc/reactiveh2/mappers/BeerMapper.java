package com.lavalliere.daniel.spring.r2dbc.reactiveh2.mappers;

import com.lavalliere.daniel.spring.r2dbc.reactiveh2.domain.Beer;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}