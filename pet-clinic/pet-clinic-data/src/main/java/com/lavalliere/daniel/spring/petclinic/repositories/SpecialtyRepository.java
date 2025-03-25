package com.lavalliere.daniel.spring.petclinic.repositories;

import com.lavalliere.daniel.spring.petclinic.model.Specialty;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepository  extends CrudRepository<Specialty, Long> {
}
