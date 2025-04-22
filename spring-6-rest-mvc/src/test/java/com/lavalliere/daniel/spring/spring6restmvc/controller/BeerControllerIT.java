package com.lavalliere.daniel.spring.spring6restmvc.controller;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.mappers.BeerMapper;
import com.lavalliere.daniel.spring.spring6restmvc.model.BeerDTO;
import com.lavalliere.daniel.spring.spring6restmvc.repositories.BeerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Load full context not a test splice
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

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
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}