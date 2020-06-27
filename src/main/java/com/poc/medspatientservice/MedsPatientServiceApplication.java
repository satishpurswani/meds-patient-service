package com.poc.medspatientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MedsPatientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedsPatientServiceApplication.class, args);
	}

}
