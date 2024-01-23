package com.laptopshop.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.laptopshop.library.*", "com.laptopshop.customer.*"})
@EnableJpaRepositories(value = "com.laptopshop.library.repository")
@EntityScan(value = "com.laptopshop.library.model")
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
