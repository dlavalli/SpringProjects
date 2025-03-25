package guru.springframework.repositories;

import guru.springframework.domain.Category;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by jt on 6/13/17.
 */
// public interface CategoryRepository extends CrudRepository<Category, String> {
public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByDescription(String description);
}
