package com.microservices.photoapp.api.accountmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppAccountMgmtMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppAccountMgmtMicroServiceApplication.class, args);
	}

}
