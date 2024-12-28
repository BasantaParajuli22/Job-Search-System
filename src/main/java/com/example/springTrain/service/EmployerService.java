package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.dto.ProfileDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.EmployerRepository;


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

	public long countAllEmployers() {
		return employerRepository.count();
	}

	public Employer findByJobPosting_JobId(Integer jobId) {
		return employerRepository.findByJobPosting_JobId(jobId);

	}

	public Employer findByEmployerIdAndJobPosting_JobId( Integer employer, Integer jobId) {
		return employerRepository.findByEmployerIdAndJobPosting_JobId(employer,jobId);

	}

	public Employer findByEmployerIdAndJobId(Integer loggedInemployerId, Integer jobId) {
		return employerRepository.findByEmployerIdAndJobPosting_JobId(loggedInemployerId,jobId);

	}

	public Employer getByEmployerIdAndJobId(Integer loggedInEmployerId, Integer jobId) {
		return employerRepository.findByEmployerIdAndJobPosting_JobId(loggedInEmployerId,jobId);
	}

	public void updateEmployer(ProfileDTO profileDTO, Employer employer) {
		employer.setCompanyName(profileDTO.getCompanyName());
  		employer.setCompanyDescription(profileDTO.getCompanyDescription());
  		employer.setAddress(profileDTO.getAddress());
    	employerRepository.save(employer); 
	}
	
}
