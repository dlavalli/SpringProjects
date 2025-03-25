package guru.springframework.pets;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Profile({"dog"})
//@Service("petService")
// Refactored to use instead guru.springframework.sfgdi.config.GreetingServiceConfig
// Usually, if you do not own the code (ie using thirdparty), you will usually use java base config
// (ie: config class) while when you own the code you will use the annotation based
public class DogPetService implements PetService {
    @Override
    public String getPetType(){
        return "Dogs are the best!";
    }
}

