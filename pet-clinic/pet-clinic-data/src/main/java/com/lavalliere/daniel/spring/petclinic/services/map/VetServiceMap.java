package com.lavalliere.daniel.spring.petclinic.services.map;


import com.lavalliere.daniel.spring.petclinic.model.Pet;
import com.lavalliere.daniel.spring.petclinic.model.Specialty;
import com.lavalliere.daniel.spring.petclinic.model.Vet;
import com.lavalliere.daniel.spring.petclinic.services.CrudService;
import com.lavalliere.daniel.spring.petclinic.services.SpecialtyService;
import com.lavalliere.daniel.spring.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    public VetServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

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
        if (vet.getSpecialities().size() > 0) {
            vet.getSpecialities().forEach(specialty -> {
                if (specialty.getId() == null) {
                    Specialty savedSpecialty = specialtyService.save(specialty);
                    specialty.setId(savedSpecialty.getId());
                }
            });
        }
        return super.save(vet);
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