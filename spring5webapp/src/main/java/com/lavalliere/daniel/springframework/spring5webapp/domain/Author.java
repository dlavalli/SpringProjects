package com.lavalliere.daniel.springframework.spring5webapp.domain;

import com.lavalliere.daniel.springframework.spring5webapp.domain.Book;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Author {

    // GenerationType.AUTO : The underlying database
    // is going to be providing the generation of this.
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    // Says the Author has a many to many relationship to books
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    // Required by JPA
    public Author() {
    }

    public Author(String firstName, String lastName, Set<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}

