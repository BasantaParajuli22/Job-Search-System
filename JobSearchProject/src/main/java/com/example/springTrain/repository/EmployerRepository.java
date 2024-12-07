package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.Users;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer>{
	
	Employer findByEmployerId(Integer employerId);
	Employer findByCompanyName(String companyName);
	Employer findByEmail(String email);
	Employer findByUsers(Users users);
	Employer findByJobPosting_JobId(Integer jobId);
	
	Employer findByEmployerIdAndJobPosting_JobId(Integer employer, Integer jobId);
	
}