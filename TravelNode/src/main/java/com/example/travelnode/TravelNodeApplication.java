package com.example.travelnode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TravelNodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelNodeApplication.class, args);
	}
}
