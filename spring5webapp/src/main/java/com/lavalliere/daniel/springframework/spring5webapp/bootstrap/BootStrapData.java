package com.lavalliere.daniel.springframework.spring5webapp.bootstrap;

import com.lavalliere.daniel.springframework.spring5webapp.domain.Author;
import com.lavalliere.daniel.springframework.spring5webapp.domain.Book;
import com.lavalliere.daniel.springframework.spring5webapp.domain.Publisher;
import com.lavalliere.daniel.springframework.spring5webapp.repositories.AuthorRepository;
import com.lavalliere.daniel.springframework.spring5webapp.repositories.BookRepository;
import com.lavalliere.daniel.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    // when Spring implements this component that's going to bring it into the Spring
    // context. It will do Dependency Injection into the constructor for an instance of
    // the author repository and also the book repository.
    public BootStrapData(AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Publisher publisher = new Publisher(
                "Addison-Wesley Professional",
                "75 Arlington Street Suite 300 ",
                "Boston",
                "Massachusetts",
                "02116");

        // Persist the data in the repositories (ie: generate the SQL STMT and run them)
        publisherRepository.save(publisher);
        System.out.println("Number of publishers: " + publisherRepository.count());

        Author eric = new Author("Eric", "Evans");
        Book ddd =new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(publisher);
        publisher.getBooks().add(ddd);

        // Persist the data in the repositories (ie: generate the SQL STMT and run them)
        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(publisher);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "3939459459");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        noEJB.setPublisher(publisher);
        publisher.getBooks().add(noEJB);

        // Persist the data in the repositories (ie: generate the SQL STMT and run them)
        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(publisher);

        System.out.println("Started in Bootstrap");
        System.out.println("Number of books: " + bookRepository.count());
        System.out.println("Publisher number of books: " + publisher.getBooks().size());
    }
}
