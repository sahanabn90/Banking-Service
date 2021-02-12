package com.demo.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SimpleBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankingApplication.class, args);
	}

}
