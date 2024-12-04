package com.example.springTrain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.EmployerRepository;
import com.example.springTrain.repository.JobSeekerRepository;
import com.example.springTrain.repository.UsersRepository;

@Service
public class UsersService {
	

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
    
    public void createJobSeeker(Users user, JobSeeker jobSeeker) {
        // Encode the user's password before saving 
    	//we save password only on user table
       user.setPassword(passwordEncoder.encode(user.getPassword())); 

       // Save the user and associate it with the JobSeeker
       usersRepository.save(user);
       jobSeeker.setUsers(user);
       jobSeekerRepository.save(jobSeeker);
    }

    
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

	public Users findByUser(Employer employer) {
		return usersRepository.findByEmployer(employer);
	}

	public Users findByJobSeeker_jobSeekerId(Integer jobSeekerId) {
		return usersRepository.findByJobSeeker_JobSeekerId(jobSeekerId);
	}

	public Users findByEmployer_employerId(Integer employerId) {
		return usersRepository.findByEmployer_EmployerId(employerId);
	}

}
