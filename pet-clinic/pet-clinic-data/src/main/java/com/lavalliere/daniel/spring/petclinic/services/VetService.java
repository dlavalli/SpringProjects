package com.lavalliere.daniel.spring.petclinic.services;

import com.lavalliere.daniel.spring.petclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findByIf(Long id);
    Vet save(Vet owner);
    Set<Vet> findAll();
}
