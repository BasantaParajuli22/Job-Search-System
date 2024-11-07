package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.springTrain.repository.EmployerRepository;
import com.example.springTrain.repository.JobSeekerRepository;
import com.example.springTrain.repository.UsersRepository;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobSeeker;
import com.example.springTrain.table.Users;

import jakarta.transaction.Transactional;

@Service
public class UsersService {
	
    private static final Logger logger = LoggerFactory.getLogger(Employer.class);

    private UsersRepository usersRepository;
    private JobSeekerRepository jobSeekerRepository;
    private EmployerRepository employerRepository;
   
    @Autowired
    public UsersService(UsersRepository usersRepository,
    		JobSeekerRepository jobSeekerRepository,
    		EmployerRepository employerRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.employerRepository = employerRepository;
    }

    // Method to find a user by username
    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);        
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Add PasswordEncoder for password hashing

    //for storing data in User table first to generate id
    //and storing user_id in jobseeker and employer table for foreign key purposes
    //and storing data on jobseeker and employer table respectively
    @Transactional
    public void createJobSeeker(Users user, JobSeeker jobSeeker) {
        // Encode the user's password before saving 
    	//we save password only on user table
       user.setPassword(passwordEncoder.encode(user.getPassword())); 

       // Save the user and associate it with the JobSeeker
       usersRepository.save(user);
       jobSeeker.setUsers(user);
       jobSeekerRepository.save(jobSeeker);
    }

    @Transactional
    public void createEmployer(Users user, Employer employer) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); 

        // Save the user first to generate the user_id and associate it with employer
        usersRepository.save(user);
        employer.setUsers(user);
        employerRepository.save(employer);
    }
    
    //for updating 
    public void updateJobSeeker(JobSeeker jobSeeker) {
        jobSeekerRepository.save(jobSeeker); 
    }
    public void updateEmployer(Employer submittedEmployer) {
    	employerRepository.save(submittedEmployer); 
    }
    public void updateUsers(Users user) {
    	usersRepository.save(user); 
    }

}
