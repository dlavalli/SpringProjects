package com.lavalliere.daniel.spring.petclinic.bootstrap;

import com.lavalliere.daniel.spring.petclinic.model.Owner;
import com.lavalliere.daniel.spring.petclinic.model.Pet;
import com.lavalliere.daniel.spring.petclinic.model.PetType;
import com.lavalliere.daniel.spring.petclinic.model.Vet;
import com.lavalliere.daniel.spring.petclinic.services.OwnerService;
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

    public DataLoader() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Daniel");
        owner1.setLastName("Lavalliere");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Sylvain");
        owner2.setLastName("Lavalliere");
        ownerService.save(owner2);

        System.out.println("Loaded owners.........");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Pauline");
        vet1.setLastName("Venditti");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Franco");
        vet2.setLastName("Venditti");
        vetService.save(vet2);

        System.out.println("Loaded vets.........");
    }
}
