package com.jarranz.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jarranz.cloud.model.Customer;
import com.jarranz.cloud.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class HelloController {

	@Value("${app.mongo.user}")
	private String user;
	@Value("${app.mongo.pass}")
	private String pass;
	@Value("${app.mongo.host}")
	private String host;
	@Value("${app.mongo.port}")
	private String port;
	@Value("${app.mongo.schema}")
	private String schema;

	@Autowired
	private CustomerRepository repository;

	@GetMapping("/hello")
	public ResponseEntity<Customer> hello() {
		System.out.println("hello()");
		System.out.println("DATABASE CONN");
		System.out.println("user: " + user);
		System.out.println("pass: " + pass);
		System.out.println("host: " + host);
		System.out.println("port: " + port);
		System.out.println("schema: " + schema);
					
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
		List<Customer> customers = repository.findByLastName("Smith");
		for (Customer customer : customers) {
			System.out.println(customer);
		}

		return ResponseEntity.ok().header("Custom-Header", "foo").body(customers.get(0));

	}

}
