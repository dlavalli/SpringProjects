package com.lavalliere.daniel.springframework.spring5webapp.bootstrap;

import com.lavalliere.daniel.springframework.spring5webapp.domain.Author;
import com.lavalliere.daniel.springframework.spring5webapp.domain.Book;
import com.lavalliere.daniel.springframework.spring5webapp.repositories.AuthorRepository;
import com.lavalliere.daniel.springframework.spring5webapp.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    // when Spring implements this component that's going to bring it into the Spring
    // context. It will do Dependency Injection into the constructor for an instance of
    // the author repository and also the book repository.
    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author("Eric", "Evans");
        Book ddd =new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        // Persist the data in the repository
        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "3939459459");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        // Persist the data in the repository
        authorRepository.save(rod);
        bookRepository.save(noEJB);

        System.out.println("Started in Bootstrap");
        System.out.println("Number of books: " + bookRepository.count());
    }
}
