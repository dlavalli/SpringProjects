package com.lavalliere.daniel.spring.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.spring6restmvc.model.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.services.BeerService;
import com.lavalliere.daniel.spring.spring6restmvc.services.BeerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)  // A TestSplice specifically limited to the specified class
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // @MockBean  //  Deprecated ...
    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeEach()
    public void setup() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        // Reflects the field(s) that would be supplied by the api endpoint caller
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        // Can replace patch(BeerController.BEER_PATH+"/"+beer.getId()  by the version below
        // since the patch method has a version that takes the host/path with a variable arguments
        mockMvc.perform(patch(BeerController.BEER_PATH_ID,beer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerMap))
            )
            .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID,beer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        // By default, it verifies for a single interaction
        verify(beerService).deleteBeerById(uuidArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH_ID,beer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beer)))
            .andExpect(status().isNoContent());

        // By default, it verifies for a single interaction
        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {
        // Method 1: create our own Object mapper
        // 1st way to use mapper, less preferred way to print the object
        // By default a LocalDateTime is as follow: "createdDate":[2025,4,21,14,18,17,299622500]

        // ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.findAndRegisterModules();

        // Method 2 : Use the ObjectMapper created with the web context
        // NOTE that the web context has an already configured object mapper that can be used
        //      instead of creating one on our own. The configured mapper has the following LocaldateTime format
        //      "createdDate":"2025-04-21T14:29:59.1762712"

        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class)))
            .willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beer))
        ).andExpect(status().isCreated())
         .andExpect(header().exists("Location"));
    }

    @Test
    public void listBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(
            get(BeerController.BEER_PATH)
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(
                get(BeerController.BEER_PATH_ID,testBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
         .andExpectAll(content().contentType(MediaType.APPLICATION_JSON))
         .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
         .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }
}