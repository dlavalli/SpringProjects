package guru.springframework.sfgdi.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dog"})
@Service("petService")
public class DogPetService implements PetService {
    @Override
    public String getPetType(){
        return "Dogs are the best!";
    }
}

