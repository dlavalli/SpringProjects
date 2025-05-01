package com.lavalliere.daniel.spring.resttemplate.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.lavalliere.daniel.spring.resttemplate.model.BeerDTO;
import com.lavalliere.daniel.spring.resttemplate.model.BeerDTOPageImpl;
import com.lavalliere.daniel.spring.resttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    static final String GET_BEER_PATH = "/api/v1/beer";
    static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_BEER_BY_ID_PATH, beerId);
    }

    @Override
    public BeerDTO updateBeer(BeerDTO beerDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_BEER_BY_ID_PATH, beerDto, beerDto.getId());
        return getBeerById(beerDto.getId());
    }

    @Override
    public BeerDTO createBeer(BeerDTO newDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // This particular api returns the location header for the created Beer
        // There are apis that will return the created object as well in the body
        // In this case BeerDTO.class is not use to bind the response but just to create the ResponseEntity
        // ResponseEntity<BeerDTO> response = restTemplate.postForEntity(GET_BEER_PATH, newDto, BeerDTO.class);

        // Use this when the body contains the created Beer
        // return response.getBody();


        // Use this when provided with location header for created Beer
        URI uri = restTemplate.postForLocation(GET_BEER_PATH, newDto);
        return restTemplate.getForObject(uri.getPath(), BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(
        String beerName,
        BeerStyle beerStyle,
        Boolean showInventory,
        Integer pageNumber,
        Integer pageSize
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);
        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }
        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", beerStyle);
        }

        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", beerStyle);
        }

        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", beerStyle);
        }

        // To implement a Page, which is an interface, you need an implementation
        // and since PageImpl does not have a public constructor, you need to implement your own
        ResponseEntity<BeerDTOPageImpl> response = restTemplate.getForEntity(
            uriComponentsBuilder.toUriString(),
            BeerDTOPageImpl.class
        );

        return response.getBody();

    }
}
