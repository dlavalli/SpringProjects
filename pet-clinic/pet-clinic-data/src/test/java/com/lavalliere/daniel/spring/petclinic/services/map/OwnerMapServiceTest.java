package com.lavalliere.daniel.spring.petclinic.services.map;

import com.lavalliere.daniel.spring.petclinic.model.BaseEntity;
import com.lavalliere.daniel.spring.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long ownerId1 = 1L;
    final Long ownerId2 = 2L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        // In the spring context, this is what is done automatically
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner owner = Owner.builder().build();
        owner.setId(ownerId1);
        owner.setLastName(lastName);
        ownerMapService.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId1);
        assertEquals(ownerId1, owner.getId());
    }

    @Test
    void saveExistingId() {
        Owner owner2 = Owner.builder().build();
        owner2.setId(ownerId2);
        Owner savedOwner = ownerMapService.save(owner2);
        assertEquals(ownerId2, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId1));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId1);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Set<Owner> owners = ownerMapService.findAll();
        Owner owner = ownerMapService.findByLastName(lastName);
        assertNotNull(owner);
        assertEquals(ownerId1, owner.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Set<Owner> owners = ownerMapService.findAll();
        Owner owner = ownerMapService.findByLastName("Foo");
        assertNull(owner);
    }
}