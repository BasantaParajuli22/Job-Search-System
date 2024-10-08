package com.example.springTrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.example")
@ComponentScan(basePackages = {"com.example.springTrain"})
public class JobSearchProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSearchProjectApplication.class, args);
	}

}
