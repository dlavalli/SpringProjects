package guru.springframework.spring6webapps.services;

import guru.springframework.spring6webapps.domain.Author;

public interface AuthorService {
    Iterable<Author> findAll();
}
