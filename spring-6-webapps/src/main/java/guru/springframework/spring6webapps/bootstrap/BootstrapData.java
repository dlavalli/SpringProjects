package guru.springframework.spring6webapps.bootstrap;

import guru.springframework.spring6webapps.domain.Author;
import guru.springframework.spring6webapps.domain.Book;
import guru.springframework.spring6webapps.domain.Publisher;
import guru.springframework.spring6webapps.repositories.AuthorRepository;
import guru.springframework.spring6webapps.repositories.BookRepository;
import guru.springframework.spring6webapps.repositories.PublisherRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner  {

    @NonNull
    @Autowired
    private final AuthorRepository  authorRepository;

    @NonNull
    @Autowired
    private final BookRepository bookRepository;

    @NonNull
    @Autowired
    private final PublisherRepository publisherRepository;


    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book book1 = new Book();
        book1.setTitle("Domain Driven Design");
        book1.setIsbn("123456");

        Author ericSaved = authorRepository.save(eric);
        Book book1Saved = bookRepository.save(book1);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book book2 = new Book();
        book2.setTitle("J2EE Development without JB");
        book2.setIsbn("567890");

        Author robSaved = authorRepository.save(rod);
        Book book2Saved = bookRepository.save(book2);

        ericSaved.getBooks().add(book1Saved);
        robSaved.getBooks().add(book2Saved);
        book1Saved.getAuthors().add(ericSaved);
        book2Saved.getAuthors().add(robSaved);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("My Publisher");
        publisher.setAddress("123 Main");
        publisher.setCity("My City");
        publisher.setZip("My Zip");
        Publisher savedPublisher = publisherRepository.save(publisher);

        System.out.printf("Publisher Count: %d%n", publisherRepository.count());
        book1Saved.setPublisher(savedPublisher);
        book2Saved.setPublisher(savedPublisher);

        authorRepository.save(ericSaved);
        authorRepository.save(robSaved);
        bookRepository.save(book1Saved);
        bookRepository.save(book2Saved);

        System.out.println("In Bootstrap");
        System.out.printf("Author Count: %d%n", authorRepository.count());
        System.out.printf("Book Count: %d%n", bookRepository.count());




    }
}
