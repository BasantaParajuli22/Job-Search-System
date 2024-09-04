package com.example.springTrain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerUserRepository extends JpaRepository<EmployerUser, Integer>{
	
	EmployerUser findByUsername(String username);
	EmployerUser findByEmail(String email);
	
}