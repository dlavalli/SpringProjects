package guru.springframework.spring6webapps.repositories;

import guru.springframework.spring6webapps.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
