package com.example.nvclothes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NvClothesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NvClothesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext ctx){

		return (args -> {

			System.out.println("hello");
		});
	}

}
