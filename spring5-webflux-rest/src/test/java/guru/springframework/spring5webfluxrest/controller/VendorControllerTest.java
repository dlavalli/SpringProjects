package guru.springframework.spring5webfluxrest.controller;

import guru.springframework.spring5webfluxrest.controller.VendorController;
import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().id("1").firstName("Daniel").lastName("Lavalliere").build(),
                                      Vendor.builder().id("2").firstName("Sylvain").lastName("Lavalliere").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(Vendor.builder().id("1").firstName("Daniel").lastName("Lavalliere").build()));

        webTestClient.get().uri("/api/v1/vendors/someid")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreatCategory() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorSaveMono = Mono.just(Vendor.builder().id("1").firstName("Daniel").lastName("Lavalliere").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorSaveMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor>vendorToUpdateToMono =  Mono.just(Vendor.builder().firstName("Daniel").lastName("Lavalliere").build());
        webTestClient.put()
                .uri("/api/v1/vendors/someid")
                .body(vendorToUpdateToMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }
}