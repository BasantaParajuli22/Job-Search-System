package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.springTrain.user.JobSeeker;
import com.example.springTrain.user.JobSeekerRepository;
import com.example.springTrain.user.User;

@Service
public class JobSeekerService {
	
	private JobSeekerRepository jobSeekerRepository;

	@Autowired
	public JobSeekerService(JobSeekerRepository jobSeekerRepository) {
		this.jobSeekerRepository = jobSeekerRepository;
	}
	
	public JobSeeker findByUser(User user) {
		return jobSeekerRepository.findByUser(user);
	}


}
