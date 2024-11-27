package com.example.springTrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.springTrain.entity")  // Adjust to your package
@EnableJpaRepositories("com.example.springTrain.repository")  // Adjust to your package
public class JobSearchProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobSearchProjectApplication.class, args);
    }
}

