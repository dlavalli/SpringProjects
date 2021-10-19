package com.example.accessingdatamongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// For more control over the registration process,
// you can use the @EnableMongoRepositories annotation.
// By default, @EnableMongoRepositories scans the current package
// for any interfaces that extend one of Spring Data’s repository interfaces.
// You can use its basePackageClasses=MyRepository.class to safely tell
// Spring Data MongoDB to scan a different root package by type if
// your project layout has multiple projects and it does not find your repositories.

@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMongodbApplication.class, args);
	}
}
