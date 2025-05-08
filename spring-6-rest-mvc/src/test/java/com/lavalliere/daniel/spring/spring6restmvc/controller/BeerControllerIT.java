package com.lavalliere.daniel.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.events.BeerCreatedEvent;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.BeerMapperImpl;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.CustomerMapperImpl;
import com.lavalliere.daniel.spring.spring6restmvc.model.BeerStyle;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.BeerMapper;
import com.lavalliere.daniel.spring.spring6restmvc.model.BeerDTO;
import com.lavalliere.daniel.spring.spring6restmvc.repositories.BeerRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static com.lavalliere.daniel.spring.spring6restmvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.core.Is.is;

@RecordApplicationEvents
@SpringBootTest  // Load full context not a test splice
@Import({BeerMapperImpl.class, CustomerMapperImpl.class})
class BeerControllerIT {
    @Autowired
    ApplicationEvents applicationEvents;

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .apply(springSecurity())
            .build();
    }

    @Test
    void testCreateBeerMVC() throws Exception {
        val beerDTO = BeerDTO.builder()
            .beerName("New Beer")
            .beerStyle(BeerStyle.IPA)
            .upc("123123")
            .price(BigDecimal.TEN)
            .quantityOnHand(5)
            .build();

        mockMvc.perform(post(BeerController.BEER_PATH)
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
            .andExpect(status().isCreated())
            .andReturn();

        Assertions.assertEquals(1, applicationEvents
            .stream(BeerCreatedEvent.class)
            .count());
    }

    @Test
    void testNoAuth() throws Exception {
        //Test No Auth
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("pageSize", "800"))
            .andExpect(status().isUnauthorized());

    }

    @Test
    void testListBeersByStyleAndNameShowInventoryTrueWithPaging() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .with(jwtRequestPostProcessor)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("showInventory", "true")
                .queryParam("pageNumber", "2")
                .queryParam("pageSize", "50"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(11)));
           // .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryTrue() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .with(jwtRequestPostProcessor)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("showInventory", "true"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(11)));
            //.andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .with(jwtRequestPostProcessor)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("showInventory", "false"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(11)));
            //.andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.nullValue()));
    }

    @Test
    void tesListBeersByStyleAndName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .with(jwtRequestPostProcessor)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(11)));
    }

    @Test
    void tesListBeersByStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .with(jwtRequestPostProcessor)
                .queryParam("beerStyle", BeerStyle.IPA.name()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(11)));
    }

    @Test
    void testListBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .with(jwtRequestPostProcessor)
                .queryParam("beerName", "IPA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(11)));
    }

    @Test
    void testPatchBeerBadName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        // Reflects the field(s) that would be supplied by the api endpoint caller
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        // Can replace patch(BeerController.BEER_PATH+"/"+beer.getId()  by the version below
        // since the patch method has a version that takes the host/path with a variable arguments
        MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID,beer.getId())
                .with(jwtRequestPostProcessor)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerMap))
        ).andExpect(status().isBadRequest())
         .andReturn();
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteBeerById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteBeerById() {
        Beer existingBeer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteBeerById(existingBeer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(existingBeer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateBeer() {
        Beer existingBeer = beerRepository.findAll().get(0);
        BeerDTO beerDto = beerMapper.beerToBeerDto(existingBeer);
        beerDto.setId(null);
        beerDto.setVersion(null);
        final String beerName = "UPDATED";
        beerDto.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateBeerById(existingBeer.getId(), beerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Beer updatedBeer = beerRepository.findById(existingBeer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Test
    @Transactional
    @Rollback
    void testSaveNewBeer() {
        BeerDTO beerDto = BeerDTO.builder()
            .beerName("New Beer")
            .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());  // Throws
        });
    }

    @Test
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeer() {
        Page<BeerDTO> page = beerController.listBeers(null, null, false, 1, 25);
        assertThat(page.getContent().size()).isEqualTo(25);
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList() {
        beerRepository.deleteAll();
        Page<BeerDTO> page = beerController.listBeers(null, null, false, 1, 25);
        assertThat(page.getContent().size()).isEqualTo(0);
    }
}