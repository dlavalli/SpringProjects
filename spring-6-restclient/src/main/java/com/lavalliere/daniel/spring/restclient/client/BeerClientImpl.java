package com.lavalliere.daniel.spring.restclient.client;


import com.lavalliere.daniel.spring.restclient.model.BeerDTO;
import com.lavalliere.daniel.spring.restclient.model.BeerDTOPageImpl;
import com.lavalliere.daniel.spring.restclient.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestClient.Builder restClientBuilder;


    static final String GET_BEER_PATH = "/api/v1/beer";
    static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

    @Override
    public void deleteBeer(UUID beerId) {
        RestClient restClient = restClientBuilder.build();
        restClient.delete()
            .uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID_PATH).build(beerId))
            .retrieve().
            toBodilessEntity();
    }

    @Override
    public BeerDTO updateBeer(BeerDTO beerDto) {
        RestClient restClient = restClientBuilder.build();
        restClient.put()
            .uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID_PATH).build(beerDto.getId()))
            .body(beerDto)
            .retrieve()
            .toBodilessEntity();

        return getBeerById(beerDto.getId());
    }

    @Override
    public BeerDTO createBeer(BeerDTO newDto) {
        RestClient restClient = restClientBuilder.build();

        val location = restClient.post()
            .uri(uriBuilder -> uriBuilder.path(GET_BEER_PATH).build())
            .body(newDto)
            .retrieve()
            .toBodilessEntity()  // Implementation just returns a location header
            .getHeaders()
            .getLocation();  // Retrieve the location url to return

        // Retrieve the newly created entity from the provided location url
        return restClient.get()
            .uri(location.getPath())
            .retrieve()
            .body(BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestClient restClient = restClientBuilder.build();
        return restClient.get()
            .uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID_PATH).build(beerId))
            .retrieve()
            .body(BeerDTO.class);
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
        RestClient restClient = restClientBuilder.build();

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

        return restClient.get()
            .uri(uriComponentsBuilder.toUriString())
            .retrieve()
            .body(BeerDTOPageImpl.class);
    }
}
