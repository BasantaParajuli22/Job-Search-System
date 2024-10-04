package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.user.Employer;
import com.example.springTrain.user.EmployerRepository;
import com.example.springTrain.user.JobSeeker;
import com.example.springTrain.user.JobSeekerRepository;
import com.example.springTrain.user.UserRepository;

import jakarta.transaction.Transactional;

import com.example.springTrain.user.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    
    @Autowired
    private EmployerRepository employerRepository;

    @Transactional
    public void createJobSeeker(User user, JobSeeker jobSeeker) {
    	
        User savedUser = userRepository.save(user);
            
        jobSeeker.setUser(savedUser);
        jobSeekerRepository.save(jobSeeker);

    }
    @Transactional
    public void createEmployer(User user, Employer employer) {
        // Save the user first to generate the user_id
        User savedUser = userRepository.save(user);

        // Link the saved user with the employer
        employer.setUser(savedUser);
        employerRepository.save(employer);
    }


}

