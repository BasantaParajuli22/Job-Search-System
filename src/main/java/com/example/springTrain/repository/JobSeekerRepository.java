package com.example.springTrain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long>{
	
	JobSeeker findByJobSeekerId(Long jobSeekerId);
	JobSeeker findByUsers(Users user);
	JobSeeker findByFullName(String name);
	
}