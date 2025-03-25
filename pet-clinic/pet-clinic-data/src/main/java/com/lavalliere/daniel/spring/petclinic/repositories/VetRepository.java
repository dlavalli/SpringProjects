package com.lavalliere.daniel.spring.petclinic.repositories;

import com.lavalliere.daniel.spring.petclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
