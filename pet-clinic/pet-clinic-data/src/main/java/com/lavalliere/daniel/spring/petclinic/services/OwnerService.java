package com.lavalliere.daniel.spring.petclinic.services;

import com.lavalliere.daniel.spring.petclinic.model.Owner;

import java.util.Set;

public interface OwnerService {
    Owner findByLastName(String lastName);
    Owner findByIf(Long id);
    Owner save(Owner owner);
    Set<Owner> findAll();
}
