package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void createJobSeeker( JobSeekerDTO jobSeekerDTO) {
    	
    	Users user = new Users();
    	JobSeeker jobSeeker = new JobSeeker();
  		user.setPassword(passwordEncoder.encode(jobSeekerDTO.getPassword())); //encoding password before saving
    	user.setEmail(jobSeekerDTO.getEmail());
  		user.setUsertype(jobSeekerDTO.getUsertype());
  		user.setUserStatus(UserStatus.UNBLOCKED);


  		// Save the user and associate it with the JobSeeker
  		jobSeeker.setUsers(user);
  		jobSeeker.setEmail(jobSeekerDTO.getEmail());
  		jobSeeker.setNumber(jobSeekerDTO.getNumber());
 		jobSeeker.setFullName(jobSeekerDTO.getFullName());
 		jobSeeker.setSkills(jobSeekerDTO.getSkills());
 		
 		usersRepository.save(user);
 		jobSeekerRepository.save(jobSeeker);
    }

	public void createEmployer(EmployerDTO employerDTO) {
		Users user = new Users();
		Employer employer = new Employer();
		user.setEmail(employerDTO.getEmail());
  		user.setPassword(passwordEncoder.encode(employerDTO.getPassword())); 
  		user.setUsertype(employerDTO.getUsertype());
  		user.setUserStatus(UserStatus.UNBLOCKED);
  		
        employer.setUsers(user);
        employer.setEmail(employerDTO.getEmail());
  		employer.setCompanyName(employerDTO.getCompanyName());
  		employer.setCompanyDescription(employerDTO.getCompanyDescription());
  		employer.setAddress(employerDTO.getAddress());
		
   		usersRepository.save(user);
        employerRepository.save(employer);
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

	public Users findByEmail(String userEmail) {
		return usersRepository.findByEmail(userEmail);
	}

	public Users findByEmployer_EmployerId(Integer employerId) {
		return usersRepository.findByEmployer_EmployerId(employerId);
	}

	public Users findByUserId(Integer userId) {
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
