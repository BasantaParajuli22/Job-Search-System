package com.example.springTrain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer>{
	
	Employer findByEmployerId(Integer employerId);
	Employer findByCompanyName(String companyName);
	Employer findByEmail(String email);
	Employer findByUser(User user);

}