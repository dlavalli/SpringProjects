package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by jt on 6/13/17.
 */
// public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
public interface UnitOfMeasureRepository extends MongoRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findFirstByDescription(String description);
}
