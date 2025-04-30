package com.lavalliere.daniel.spring.spring6restmvc.repositories;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import com.lavalliere.daniel.spring.spring6restmvc.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().getFirst();
    }

    @Transactional
    @Test
    void testAddCategory() {
        Category savedCat = categoryRepository.save(Category.builder()
            .description("Ales")
            .build());

        testBeer.addCategory(savedCat);
        Beer saveBeer = beerRepository.save(testBeer);

        System.out.println(saveBeer.getBeerName());

    }
}