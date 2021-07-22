package com.lavalliere.daniel.spring.petclinic.services.map;


import com.lavalliere.daniel.spring.petclinic.model.Pet;
import com.lavalliere.daniel.spring.petclinic.model.Vet;
import com.lavalliere.daniel.spring.petclinic.services.CrudService;
import com.lavalliere.daniel.spring.petclinic.services.VetService;

import java.util.Set;

public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {
    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {
        return super.save(vet.getId(), vet);
    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
