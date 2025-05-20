package com.lavalliere.daniel.spring.r2dbc.reactiveh2.controller;

import com.lavalliere.daniel.spring.r2dbc.reactiveh2.domain.Beer;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.model.BeerDTO;
import com.lavalliere.daniel.spring.r2dbc.reactiveh2.repopsitories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

// Annotation that can be applied to a test class to enable a WebTestClient
// that is bound directly to the application. Tests do not rely upon an HTTP server
// and use mock requests and responses.
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(999)
    void testDeleteBeer() {
        webTestClient.delete()
            .uri(BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus()
            .isNoContent();
    }


    @Test
    @Order(3)
    void testUpdateBeer() {
        webTestClient.put()
            .uri(BeerController.BEER_PATH_ID, 1)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    void testCreateBeer() {
        webTestClient.post().uri(BeerController.BEER_PATH)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }


    @Test
    @Order(1)
    void testGetTestById() {
        webTestClient.get().uri(BeerController.BEER_PATH_ID, 1)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type","application/json")
            .expectBody(BeerDTO.class);
    }

    @Test
    @Order(2)
    void testListBeers() {
        webTestClient.get().uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("Content-Type", "application/json")
            .expectBody().jsonPath("$.size()").isEqualTo(3);
    }
}