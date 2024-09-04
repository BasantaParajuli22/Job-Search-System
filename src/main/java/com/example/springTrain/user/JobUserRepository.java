package com.example.springTrain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobUserRepository extends JpaRepository<JobUser, Integer>{
	
	JobUser findByUsername(String username);
	JobUser findByEmail(String email);
	
}