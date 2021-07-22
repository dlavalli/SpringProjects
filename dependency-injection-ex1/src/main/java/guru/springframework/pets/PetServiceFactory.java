package guru.springframework.pets;

public class PetServiceFactory {
    public PetService getPetService(String petType) {
        switch (petType) {
            case "cat":
                return new CatPetservice();
            case "dog":
            default:
                return new DogPetService();
        }
    }
}
