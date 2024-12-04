package com.example.springTrain.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springTrain.entity.Users;
import com.example.springTrain.enums.Usertype;
import com.example.springTrain.repository.UsersRepository;


@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            String adminPassword = "admin123";
            // Check if the admin username exists in the repository
            Users existingAdmin = userRepository.findByUsername(adminUsername);
            
            if (existingAdmin == null) {  // If no admin is found, create one
                Users admin = new Users();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword)); // Password encoding
                admin.setUsertype(Usertype.ADMIN);  // Assign role as Admin
                userRepository.save(admin);  // Save to the database
            }
        };
    }
}
