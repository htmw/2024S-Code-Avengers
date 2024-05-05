package com.bookbuddy.bookbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info= @Info(title="Bookbuddy API", version="1.0", description="Bookbuddy API Information"))
public class BookbuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookbuddyApplication.class, args);
	}

}
