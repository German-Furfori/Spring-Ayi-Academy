package com.ayi.curso.rest.serv.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EntityScan(basePackages = { "com.ayi.curso.rest.serv.app.entities" })
@EnableJpaRepositories(basePackages = { "com.ayi.curso.rest.serv.app.repositories" })
public class AyiSpringRestfulApplication { // Esta app es la que hace arrancar tdo

	public static void main(String[] args) {
		SpringApplication.run(AyiSpringRestfulApplication.class, args);
	}

}
