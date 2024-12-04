package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Users findByUsername(String username);
	Users findByEmail(String email);
	Users findByEmailAndPassword(String email,String password);
	Users findByEmployer(Employer employer);
	
	Users findByJobSeeker_JobSeekerId(Integer jobSeekerId);
	Users findByEmployer_EmployerId(Integer employerId);
}