package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springTrain.dto.EmployerDTO;
import com.example.springTrain.dto.JobSeekerDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.enums.UserStatus;
import com.example.springTrain.repository.EmployerRepository;
import com.example.springTrain.repository.JobSeekerRepository;
import com.example.springTrain.repository.UsersRepository;

@Service
public class UsersService {
	

    private UsersRepository usersRepository;
    private JobSeekerRepository jobSeekerRepository;
    private EmployerRepository employerRepository;
   
    public UsersService(UsersRepository usersRepository,
    		JobSeekerRepository jobSeekerRepository,
    		EmployerRepository employerRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.employerRepository = employerRepository;
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Add PasswordEncoder for password hashing

    
    //for storing data in User table first to generate id
    //and storing user_id in jobseeker and employer table for foreign key purposes
    //and storing data on jobseeker and employer table respectively
    
    @Transactional
    public void createJobSeeker( JobSeekerDTO jobSeekerDTO) {
    	
    	Users user = new Users();
    	JobSeeker jobSeeker = new JobSeeker();
  		user.setPassword(passwordEncoder.encode(jobSeekerDTO.getPassword())); //encoding password before saving
    	user.setEmail(jobSeekerDTO.getEmail().toLowerCase());
  		user.setUsertype(jobSeekerDTO.getUsertype());
  		user.setUserStatus(UserStatus.UNBLOCKED);

  		// Save the user and associate it with the JobSeeker
  		jobSeeker.setUsers(user);
 		jobSeeker.setFullName(jobSeekerDTO.getFullName());
 		
 		usersRepository.save(user);
 		jobSeekerRepository.save(jobSeeker);
    }
    
    @Transactional
	public void createEmployer(EmployerDTO employerDTO) {
		Users user = new Users();
		Employer employer = new Employer();
		user.setEmail(employerDTO.getEmail().toLowerCase());
  		user.setPassword(passwordEncoder.encode(employerDTO.getPassword())); 
  		user.setUsertype(employerDTO.getUsertype());
  		user.setUserStatus(UserStatus.UNBLOCKED);
  		
        employer.setUsers(user);    
  		employer.setCompanyName(employerDTO.getCompanyName().toLowerCase());
  		
   		usersRepository.save(user);
        employerRepository.save(employer);
	}
    
    public void updateUsers(Users user) {
    	usersRepository.save(user); 
    }

	public Users findByUser(Employer employer) {
		return usersRepository.findByEmployer(employer);
	}

	public Users findByJobSeeker_jobSeekerId(Long jobSeekerId) {
		return usersRepository.findByJobSeeker_JobSeekerId(jobSeekerId);
	}

	public Users findByEmployer_employerId(Long employerId) {
		return usersRepository.findByEmployer_EmployerId(employerId);
	}

	public Users findByEmail(String userEmail) {
		return usersRepository.findByEmail(userEmail);
	}

	public Users findByEmployer_EmployerId(Long employerId) {
		return usersRepository.findByEmployer_EmployerId(employerId);
	}

	public Users findByUserId(Long userId) {
		return usersRepository.findByUserId(userId);
		
	}

	public void blockUser(Users user) {
		
		user.setUserStatus(UserStatus.BLOCKED);		
		usersRepository.save(user); 
	}

	public void unBlockUser(Users user) {
		
		user.setUserStatus(UserStatus.UNBLOCKED);
		usersRepository.save(user); 
	}



}
