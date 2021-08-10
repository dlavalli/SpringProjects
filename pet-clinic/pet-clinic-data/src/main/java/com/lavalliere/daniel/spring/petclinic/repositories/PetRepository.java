package com.lavalliere.daniel.spring.petclinic.repositories;


import com.lavalliere.daniel.spring.petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
