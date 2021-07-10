package com.lavalliere.daniel.spring.petclinic.services;

import com.lavalliere.daniel.spring.petclinic.model.Pet;
import java.util.Set;
public interface PetService {
    Pet findByIf(Long id);
    Pet save(Pet owner);
    Set<Pet> findAll();
}
