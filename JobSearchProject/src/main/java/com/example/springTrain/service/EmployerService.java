package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.repository.EmployerRepository;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.Users;


@Service
public class EmployerService {
	
    //private static final Logger logger = LoggerFactory.getLogger(Employer.class);
		
	private EmployerRepository employerRepository;

	@Autowired
	public EmployerService(EmployerRepository employerRepository) {
		this.employerRepository = employerRepository;
	}
		
	public Employer findByUser(Users user) {
		return employerRepository.findByUsers(user);
	}
	public Employer findByCompanyName(String companyName) {
		return employerRepository.findByCompanyName(companyName);
	}

	public Employer findByEmployerId(int employerId) {
		return employerRepository.findByEmployerId(employerId);
	}

	
	//to find all employer
    public List<Employer> findAllEmployers() {
        return employerRepository.findAll();
    }
	
}
