package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springTrain.user.Employer;
import com.example.springTrain.user.EmployerRepository;
import com.example.springTrain.user.JobSeeker;
import com.example.springTrain.user.JobSeekerRepository;
import com.example.springTrain.user.UserRepository;
import com.example.springTrain.user.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Add PasswordEncoder for password hashing

    
    @Transactional
    public void createJobSeeker(User user, JobSeeker jobSeeker) {
        // Encode the user's password before saving 
    	//we save password only on user table
        user.setPassword(passwordEncoder.encode(user.getPassword())); 

        // Save the user first to generate the user_id
        User savedUser = userRepository.save(user);

        // Set the role for JobSeeker
        jobSeeker.setUser(savedUser);
        jobSeekerRepository.save(jobSeeker);
    }

    @Transactional
    public void createEmployer(User user, Employer employer) {
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword())); 

        // Save the user first to generate the user_id
        User savedUser = userRepository.save(user);

        // Set the role for Employer
        employer.setUser(savedUser);
        employerRepository.save(employer);
    }
}
