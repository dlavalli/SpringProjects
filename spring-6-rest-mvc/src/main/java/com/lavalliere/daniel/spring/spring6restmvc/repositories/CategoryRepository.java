package com.lavalliere.daniel.spring.spring6restmvc.repositories;


import com.lavalliere.daniel.spring.spring6restmvc.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}