package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.Users;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long>{
	
	Employer findByEmployerId(Long employerId);
	Employer findByCompanyName(String companyName);
	Employer findByUsers(Users users);
	Employer findByJobPosting_JobId(Long jobId);
	
	Employer findByEmployerIdAndJobPosting_JobId(Long employer, Long jobId);
	
}