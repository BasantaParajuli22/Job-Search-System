package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.user.Employer;
import com.example.springTrain.user.EmployerRepository;
import com.example.springTrain.user.User;


@Service
public class EmployerService {
		
	private EmployerRepository employerRepository;

	@Autowired
	public EmployerService(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
	}
		
	public Employer findByUser(User user) {
		return employerRepository.findByUser(user);
	}
}
