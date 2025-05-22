package com.lavalliere.danie.spring.webclient.client;

import com.lavalliere.danie.spring.webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {
    @Autowired
    BeerClient beerClient;

    @Test
    void testListBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testListBeerMap() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerMap().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerJson() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeersJsonNode().subscribe(jsonNode -> {
            System.out.println(jsonNode.toPrettyString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerDto() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerDtos().subscribe(dto -> {
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDtos()
            // FlatMap required here because publisher (ie: Mono<>) is returned bye getBeerById
            .flatMap(dto -> beerClient.getBeerById(dto.getId()))
            .subscribe(byIdDto -> {
                System.out.println(byIdDto.getBeerName());
                atomicBoolean.set(true);
            });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.getBeerByBeerStyle("Pale Ale")
            .subscribe(dto -> {
                System.out.println(dto.toString());
                atomicBoolean.set(true);
            });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testCreateBeer() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
            .price(new BigDecimal("10.99"))
            .beerName("Mango Bobs")
            .beerStyle("IPA")
            .quantityOnHand(500)
            .upc("123245")
            .build();

        beerClient.createBeer(newDto)
            .subscribe(dto -> {
                System.out.println(dto.toString());
                atomicBoolean.set(true);
            });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testUpdate() {

        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerDtos().next()
            .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
            .flatMap(dto -> beerClient.updateBeer(dto))
            .subscribe(byIdDto -> {
                System.out.println(byIdDto.toString());
                atomicBoolean.set(true);
            });

        await().untilTrue(atomicBoolean);
    }


    @Test
    void testPatch() {
        final String NAME = "New Name";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerDtos().next()
            .map(beerDTO -> BeerDTO.builder().beerName(NAME).id(beerDTO.getId()).build())
            .flatMap(dto -> beerClient.patchBeer(dto))
            .subscribe(byIdDto -> {
                System.out.println(byIdDto.toString());
                atomicBoolean.set(true);
            });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testDeleteBeerById() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        beerClient.listBeerDtos().next()
            // FlatMap required here because publisher (ie: Mono<>) is returned bye getBeerById
            .flatMap(dto -> beerClient.deleteBeer(dto.getId()))
            .doOnSuccess(byIdDto -> atomicBoolean.set(true))
            .subscribe();

        await().untilTrue(atomicBoolean);
    }


}