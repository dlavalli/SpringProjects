package guru.springframework.spring6webapps.repositories;

import guru.springframework.spring6webapps.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository  extends CrudRepository<Author, Long> {

}
