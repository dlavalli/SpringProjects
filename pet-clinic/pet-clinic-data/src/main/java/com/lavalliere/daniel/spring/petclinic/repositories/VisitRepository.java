package com.lavalliere.daniel.spring.petclinic.repositories;

import com.lavalliere.daniel.spring.petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
