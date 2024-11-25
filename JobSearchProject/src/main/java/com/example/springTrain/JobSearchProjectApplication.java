package com.example.springTrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.springTrain")
public class JobSearchProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSearchProjectApplication.class, args);
	}

}
