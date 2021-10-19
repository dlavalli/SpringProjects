package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jt on 6/13/17.
 */
// public interface RecipeRepository extends CrudRepository<Recipe, String> {
public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
