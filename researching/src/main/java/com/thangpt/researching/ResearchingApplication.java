package com.thangpt.researching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.thangpt.*"})
@EntityScan(basePackages = {"com.thangpt.*"})
@EnableTransactionManagement
public class ResearchingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchingApplication.class, args);
	}

}
