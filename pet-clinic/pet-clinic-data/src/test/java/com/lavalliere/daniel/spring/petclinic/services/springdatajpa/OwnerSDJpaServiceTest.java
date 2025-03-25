package com.lavalliere.daniel.spring.petclinic.services.springdatajpa;

import com.lavalliere.daniel.spring.petclinic.model.Owner;
import com.lavalliere.daniel.spring.petclinic.repositories.OwnerRepository;
import com.lavalliere.daniel.spring.petclinic.repositories.PetRepository;
import com.lavalliere.daniel.spring.petclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // JUnit 5 for mockito
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnedOwner1;

    private final String LAST_NAME = "Smith";
    private Long ownerId1 = 1L;
    private Long ownerId2 = 2L;

    @BeforeEach
    void setUp() {
        returnedOwner1 = Owner.builder().build();
        returnedOwner1.setLastName(LAST_NAME);
        returnedOwner1.setId(ownerId1);
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnedOwner1);
        Owner owner = ownerRepository.findByLastName("Smith");
        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Owner returnedOwner2 = Owner.builder().build();
        returnedOwner2.setId(ownerId2);
        Set<Owner> returnOwnersSet = new HashSet<>();
        returnOwnersSet.add(returnedOwner1);

        returnOwnersSet.add(returnedOwner2);

        when(ownerRepository.findAll()).thenReturn(returnOwnersSet);

        Set<Owner> owners = service.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnedOwner1));
        Owner owner = service.findById(ownerId1);
        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = service.findById(ownerId1);
        assertNull(owner);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(returnedOwner1);
        Owner savedOwner = service.save(returnedOwner1);
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(returnedOwner1);

        //default is 1 times
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(ownerId1);
        verify(ownerRepository).deleteById(anyLong());
    }
}