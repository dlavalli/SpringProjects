package guru.springframework.spring6webapps.services;

import guru.springframework.spring6webapps.domain.Book;

public interface BookService {
    Iterable<Book> findAll();
}
