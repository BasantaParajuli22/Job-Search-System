package com.example.springTrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class JobSearchProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSearchProjectApplication.class, args);
	}

}
