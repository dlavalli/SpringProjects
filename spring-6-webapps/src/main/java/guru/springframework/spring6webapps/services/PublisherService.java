package guru.springframework.spring6webapps.services;

import guru.springframework.spring6webapps.domain.Book;
import guru.springframework.spring6webapps.domain.Publisher;

public interface PublisherService {
    Iterable<Publisher> findAll();
}
