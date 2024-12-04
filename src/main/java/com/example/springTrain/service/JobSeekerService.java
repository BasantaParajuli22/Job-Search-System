package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.JobSeekerRepository;

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

	public JobSeeker findByUsername(String jobSeekerUsername) {
		return jobSeekerRepository.findByJobSeekerUsername(jobSeekerUsername);
	}

	public JobSeeker findByJobSeekerId(Integer jobseekerId) {
		return jobSeekerRepository.findByJobSeekerId(jobseekerId);

	}
	public JobSeeker findIdByJobSeekerId(Integer jobseekerId) {
		return jobSeekerRepository.findByJobSeekerId(jobseekerId);

	}

	public long countAlljobSeekers() {
		return jobSeekerRepository.count();
	}

}
