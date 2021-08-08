package com.lavalliere.daniel.spring.recipewebapp.repositories;

import com.lavalliere.daniel.spring.recipewebapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
