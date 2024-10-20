package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.springTrain.repository.JobSeekerRepository;
import com.example.springTrain.user.JobSeeker;
import com.example.springTrain.user.Users;

@Service
public class JobSeekerService {
	
	private JobSeekerRepository jobSeekerRepository;

	@Autowired
	public JobSeekerService(JobSeekerRepository jobSeekerRepository) {
		this.jobSeekerRepository = jobSeekerRepository;
	}
	
	//to find user from repository of JobSeeker
	public JobSeeker findByUsers(Users user) {
		return jobSeekerRepository.findByUsers(user);
	}

	//to find all jobSeekers
	public List<JobSeeker> findAllJobSeekers() {
		return jobSeekerRepository.findAll();
	}
}
