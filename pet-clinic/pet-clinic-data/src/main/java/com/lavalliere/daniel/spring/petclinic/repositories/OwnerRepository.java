package com.lavalliere.daniel.spring.petclinic.repositories;

import com.lavalliere.daniel.spring.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    // Leveraging Spring Data JPA which implements the auto based on CrudRepository
    Owner findByLastName(String lastName);
}
