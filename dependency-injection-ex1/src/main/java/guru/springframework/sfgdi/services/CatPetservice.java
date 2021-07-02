package guru.springframework.sfgdi.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"cat", "default"})
@Service("petService")
public class CatPetservice implements PetService {
    @Override
    public String getPetType() {
        return "Cats Are the Best!";
    }
}
