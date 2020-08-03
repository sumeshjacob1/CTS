package com.cts.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HospitalManagement {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagement.class, args);
	}

}
