package com.microservices.photoapp.api.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppUsersMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppUsersMicroServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		// Any dependent class which is not annotated with @Component, @Configuration, @Service, @Controller, @Repository needs to be supplied as a @Bean
		return new BCryptPasswordEncoder();
	}

}
