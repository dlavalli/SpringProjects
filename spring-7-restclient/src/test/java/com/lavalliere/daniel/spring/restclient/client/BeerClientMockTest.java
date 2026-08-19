package com.lavalliere.daniel.spring.restclient.client;

import com.lavalliere.daniel.spring.restclient.config.OAuthClientInterceptor;
import com.lavalliere.daniel.spring.restclient.config.RestClientBuilderConfig;
import com.lavalliere.daniel.spring.restclient.model.BeerDTO;
import com.lavalliere.daniel.spring.restclient.model.BeerDTOPageImpl;
import com.lavalliere.daniel.spring.restclient.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.core.exc.JacksonIOException;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@RestClientTest({BeerClient.class, BeerClientImpl.class})
@Import(RestClientBuilderConfig.class)
public class BeerClientMockTest {

    static final String URL = "http://localhost:8080";
    public static final String BEARER_TEST = "Bearer test";

    @Autowired
    BeerClient beerClient;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    JsonMapper jsonMapper;

    @MockitoBean
    OAuth2AuthorizedClientManager manager;

    BeerDTO dto;
    String dtoJson;


    // Create an in memory configuration
    @TestConfiguration
    @Import(RestClientBuilderConfig.class)
    public static class TestConfig {

        @Bean
        ClientRegistrationRepository clientRegistrationRepository() {
            return new InMemoryClientRegistrationRepository(ClientRegistration
                .withRegistrationId("springauth")   // from our application.properties
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientId("test")
                .tokenUri("test")
                .build());
        }

        @Bean
        OAuth2AuthorizedClientService auth2AuthorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository
        ){
            return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
        }

        @Bean
        OAuthClientInterceptor oAuthClientInterceptor(
            OAuth2AuthorizedClientManager manager,
            ClientRegistrationRepository clientRegistrationRepository
        ){
            return new OAuthClientInterceptor(manager, clientRegistrationRepository);
        }
    }

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeEach
    void setUp() throws JacksonIOException {
        // Setup mocked authorization
        ClientRegistration clientRegistration = clientRegistrationRepository
            .findByRegistrationId("springauth");

        OAuth2AccessToken token = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
            "test", Instant.MIN, Instant.MAX);

        when(manager.authorize(any())).thenReturn(new OAuth2AuthorizedClient(clientRegistration,
            "test", token));

        dto = getBeerDto();
        dtoJson = jsonMapper.writeValueAsString(dto);
    }

    @Test
    void testListBeersWithQueryParam() throws JacksonIOException {
        String response = jsonMapper.writeValueAsString(getPage());

        URI uri = UriComponentsBuilder
            .fromUriString(URL + BeerClientImpl.GET_BEER_PATH)
            .queryParam("beerName", "ALE")
            .build().toUri();

        server.expect(method(HttpMethod.GET))
            .andExpect(requestTo(uri))
            .andExpect(header("Authorization", BEARER_TEST))
            .andExpect(queryParam("beerName", "ALE"))
            .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        Page<BeerDTO> responsePage = beerClient
            .listBeers("ALE", null, null, null, null);

        assertThat(responsePage.getContent().size()).isEqualTo(1);
    }


    @Test
    void testDeleteNotFound() {
        server.expect(method(HttpMethod.DELETE))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH,
                dto.getId()))
            .andExpect(header("Authorization", BEARER_TEST))
            .andRespond(withResourceNotFound());

        assertThrows(HttpClientErrorException.class, () -> {
            beerClient.deleteBeer(dto.getId());
        });

        server.verify();
    }

    @Test
    void testDeleteBeer() {
        server.expect(method(HttpMethod.DELETE))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
            .andExpect(header("Authorization", BEARER_TEST))
            .andRespond(withNoContent());

        beerClient.deleteBeer(dto.getId());

        server.verify();
    }

    @Test
    void testUpdateBeer() {
        server.expect(method(HttpMethod.PUT))
            .andExpect(requestToUriTemplate(URL + BeerClientImpl.GET_BEER_BY_ID_PATH,
                dto.getId()))
            .andExpect(header("Authorization", BEARER_TEST))
            .andRespond(withNoContent());

        mockGetOperation();

        BeerDTO responseDto = beerClient.updateBeer(dto);
        assertThat(responseDto.getId()).isEqualTo(dto.getId());
    }

    @Test
    void testCreateBeer()  {
        URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.GET_BEER_BY_ID_PATH)
            .build(dto.getId());

        server.expect(method(HttpMethod.POST))
            .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
            .andExpect(header("Authorization", BEARER_TEST))
            .andRespond(withAccepted().location(uri));

        mockGetOperation();

        BeerDTO responseDto = beerClient.createBeer(dto);
        assertThat(responseDto.getId()).isEqualTo(dto.getId());
    }

    @Test
    void testGetById() {

        mockGetOperation();

        BeerDTO responseDto = beerClient.getBeerById(dto.getId());
        assertThat(responseDto.getId()).isEqualTo(dto.getId());
    }

    private void mockGetOperation() {
        server.expect(method(HttpMethod.GET))
            .andExpect(requestToUriTemplate(URL +
                BeerClientImpl.GET_BEER_BY_ID_PATH, dto.getId()))
            .andExpect(header("Authorization", BEARER_TEST))
            .andRespond(withSuccess(dtoJson, MediaType.APPLICATION_JSON));
    }

    @Test
    void testListBeers() throws JacksonIOException {
        String payload = jsonMapper.writeValueAsString(getPage());

        server.expect(method(HttpMethod.GET))
            .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
            .andExpect(header("Authorization", BEARER_TEST))
            .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        Page<BeerDTO> dtos = beerClient.listBeers();
        assertThat(dtos.getContent().size()).isGreaterThan(0);
    }

    BeerDTO getBeerDto(){
        return BeerDTO.builder()
            .id(UUID.randomUUID())
            .price(new BigDecimal("10.99"))
            .beerName("Mango Bobs")
            .beerStyle(BeerStyle.IPA)
            .quantityOnHand(500)
            .upc("123245")
            .build();
    }

    BeerDTOPageImpl getPage(){
        return new BeerDTOPageImpl(Arrays.asList(getBeerDto()), 1, 25, 1);
    }
}