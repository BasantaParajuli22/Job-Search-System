package com.example.springTrain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.user.JobSeeker;
import com.example.springTrain.user.Users;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer>{
	
	JobSeeker findByJobSeekerId(Integer jobSeekerId);
	JobSeeker findByEmail(String email);
	JobSeeker findByUsers(Users user);
	JobSeeker findByJobSeekerUsername(String jobSeekerUsername);
	
}