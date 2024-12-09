package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Users findByUserId(Integer userId);
	Users findByEmail(String email);
	Users findByEmployer(Employer employer);
	
	Users findByJobSeeker_JobSeekerId(Integer jobSeekerId);
	Users findByEmployer_EmployerId(Integer employerId);
}