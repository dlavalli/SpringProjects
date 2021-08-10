package com.lavalliere.daniel.spring.petclinic.repositories;

import com.lavalliere.daniel.spring.petclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
