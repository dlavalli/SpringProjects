package com.lavalliere.daniel.spring.receipewebapp.repositories;

import com.lavalliere.daniel.spring.receipewebapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
