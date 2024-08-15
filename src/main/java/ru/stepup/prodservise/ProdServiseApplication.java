package ru.stepup.prodservise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProdServiseApplication {
	public static void main(String[] args) {
		System.getProperties().put( "server.port", 8080 );
		SpringApplication.run(ProdServiseApplication.class, args);
	}
}
