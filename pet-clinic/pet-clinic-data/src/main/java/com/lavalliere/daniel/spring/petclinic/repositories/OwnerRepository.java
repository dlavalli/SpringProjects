package com.lavalliere.daniel.spring.petclinic.repositories;

import com.lavalliere.daniel.spring.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    // Leveraging Spring Data JPA which implements the auto based on CrudRepository
    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameLike(String lastName);
}
