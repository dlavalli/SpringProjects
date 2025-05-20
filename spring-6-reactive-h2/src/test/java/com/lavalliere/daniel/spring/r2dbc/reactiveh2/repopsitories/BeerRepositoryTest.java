package com.lavalliere.daniel.spring.r2dbc.reactiveh2.repopsitories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.config.DatabaseConfig;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.domain.Beer;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.mappers.BeerMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
// Required since we are running a test slice for BeerRepository and other classes will not be loaded
@Import({DatabaseConfig.class})
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    // Generate JSON body for a test
    void testCreateJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(
            objectMapper.writeValueAsString(
                getTestBeer()
            )
        );
    }

    @Test
    void saveNewBeer() {
        beerRepository.save(getTestBeer()).subscribe(System.out::println);
    }

    public static Beer getTestBeer() {
        return Beer.builder()
            .beerName("Space Dust")
            .beerStyle("IPA")
            .price(BigDecimal.TEN)
            .quantityOnHand(12)
            .upc("123213")
            .build();
    }
}