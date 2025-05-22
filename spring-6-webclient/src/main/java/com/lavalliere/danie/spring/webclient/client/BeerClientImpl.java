package com.lavalliere.danie.spring.webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.lavalliere.danie.spring.webclient.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Service
public class BeerClientImpl implements BeerClient {

    public final static String BEER_PATH = "/api/v3/beer";
    public final static String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final WebClient webClient;


    public BeerClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Flux<String> listBeer() {
        return webClient.get().uri(BEER_PATH)
            .retrieve()
            .bodyToFlux(String.class);
    }

    @Override
    public Flux<Map> listBeerMap() {
        return webClient.get().uri(BEER_PATH)
            .retrieve()
            .bodyToFlux(Map.class);
    }

    @Override
    public Flux<JsonNode> listBeersJsonNode() {
        return webClient.get().uri(BEER_PATH)
            .retrieve()
            .bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<BeerDTO> listBeerDtos() {
        return webClient.get().uri(BEER_PATH)
            .retrieve()
            .bodyToFlux(BeerDTO.class);
    }

    @Override
    public Mono<BeerDTO> getBeerById(String id) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(id))
            .retrieve()
            .bodyToMono(BeerDTO.class);
    }

    @Override
    public Flux<BeerDTO> getBeerByBeerStyle(String beerStyle) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(BEER_PATH)
                .queryParam("beerStyle", beerStyle)
                .build()
            )
            .retrieve()
            .bodyToFlux(BeerDTO.class);
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        return webClient.post()
            .uri(BEER_PATH).body(Mono.just(beerDTO), BeerDTO.class)
            .retrieve()
            .toBodilessEntity()

            // The API does not return the body of the created object but stores the Location header
            // So flatmap because nested op to get the header
            .flatMap(voidResponseEntity -> Mono.just(voidResponseEntity
                .getHeaders().get("Location").get(0)))
            .map(path -> path.split("/")[path.split("/").length -1])

            // flatmap because nested op to get the beer object
            .flatMap(this::getBeerById);
    }

    @Override
    public Mono<BeerDTO> updateBeer(BeerDTO beerDTO) {
        return webClient.put()
            .uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(beerDTO.getId()))
            .body(Mono.just(beerDTO), BeerDTO.class)
            .retrieve()
            .toBodilessEntity()
            .flatMap(voidResponseEntity -> getBeerById(beerDTO.getId()));
    }

    @Override
    public Mono<BeerDTO> patchBeer(BeerDTO beerDTO) {
        return webClient.patch()
            .uri(uriBuilder -> uriBuilder.path(BEER_PATH_ID).build(beerDTO.getId()))
            .body(Mono.just(beerDTO), BeerDTO.class)
            .retrieve()
            .toBodilessEntity()
            .flatMap(voidResponseEntity -> getBeerById(beerDTO.getId()));
    }

    @Override
    public Mono<Void> deleteBeer(String beerId) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder
                .path(BEER_PATH_ID)
                .build(beerId)
            )
            .retrieve()
            .bodyToMono(Void.class);
    }
}
