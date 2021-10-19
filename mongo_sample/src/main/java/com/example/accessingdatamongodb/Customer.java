package com.example.accessingdatamongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// MongoDB stores data in collections. Spring Data MongoDB maps
// the Customer class into a collection called customer. If you want
// to change the name of the collection, you can use
// Spring Data MongoDB’s @Document annotation on the class.
public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}