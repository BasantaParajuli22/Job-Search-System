package com.example.springTrain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.springTrain.repository.EmployerRepository;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.user.Employer;
import com.example.springTrain.user.Users;


@Service
public class EmployerService {
	
    private static final Logger logger = LoggerFactory.getLogger(Employer.class);
		
	private EmployerRepository employerRepository;

	@Autowired
	public EmployerService(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
	}
		
	public Employer findByUser(Users user) {
		return employerRepository.findByUsers(user);
	}
	public Employer findByCompanyName(String user) {
		return employerRepository.findByCompanyName(user);
	}

	public Employer findByEmployerId(int employerId) {
		return employerRepository.findByEmployerId(employerId);
	}
	
	//to find all employer
    public List<Employer> findAllEmployers() {
        return employerRepository.findAll();
    }
	
}
