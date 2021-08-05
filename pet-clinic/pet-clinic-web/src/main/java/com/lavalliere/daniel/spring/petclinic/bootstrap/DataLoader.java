package com.lavalliere.daniel.spring.petclinic.bootstrap;

import com.lavalliere.daniel.spring.petclinic.model.*;
import com.lavalliere.daniel.spring.petclinic.services.OwnerService;
import com.lavalliere.daniel.spring.petclinic.services.PetTypeService;
import com.lavalliere.daniel.spring.petclinic.services.SpecialtyService;
import com.lavalliere.daniel.spring.petclinic.services.VetService;
import com.lavalliere.daniel.spring.petclinic.services.map.OwnerServiceMap;
import com.lavalliere.daniel.spring.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;

    // @Autowired   is implied
    public DataLoader(OwnerService ownerService,
                      VetService vetService,
                      PetTypeService petTypeService,
                      SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Daniel");
        owner1.setLastName("Lavalliere");
        owner1.setAddress("1234 Who Cares");
        owner1.setCity("Montreal");
        owner1.setTelephone("1234567890");
        ownerService.save(owner1);

        Pet danielPet = new Pet();
        danielPet.setPetType(savedDogPetType);
        danielPet.setOwner(owner1);
        danielPet.setBirthDate(LocalDate.now());
        danielPet.setName("MyDog");
        owner1.getPets().add(danielPet);

        Owner owner2 = new Owner();
        owner2.setFirstName("Sylvain");
        owner2.setLastName("Lavalliere");
        owner2.setAddress("5678 Not Me");
        owner2.setCity("Montreal");
        owner2.setTelephone("1234567891");
        ownerService.save(owner2);

        Pet sylvainPet = new Pet();
        sylvainPet.setPetType(savedCatPetType);
        sylvainPet.setOwner(owner2);
        sylvainPet.setBirthDate(LocalDate.now());
        sylvainPet.setName("MyCat");
        owner2.getPets().add(sylvainPet);

        System.out.println("Loaded owners.........");

        Vet vet1 = new Vet();
        vet1.setFirstName("Pauline");
        vet1.setLastName("Venditti");
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Franco");
        vet2.setLastName("Venditti");
        vet2.getSpecialities().add(savedSurgery);
        vetService.save(vet2);

        System.out.println("Loaded vets.........");
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) loadData();
    }
}
