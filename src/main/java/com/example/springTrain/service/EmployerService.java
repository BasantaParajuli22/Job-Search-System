package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springTrain.dto.ProfileDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.EmployerRepository;


@Service
public class EmployerService {
			
	private final EmployerRepository employerRepository;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public EmployerService(EmployerRepository employerRepository,
			FileStorageService fileStorageService) {
		this.employerRepository = employerRepository;
		this.fileStorageService = fileStorageService;
	}
		
	public Employer findByUser(Users user) {
		return employerRepository.findByUsers(user);
	}
	public Employer findByCompanyName(String companyName) {
		return employerRepository.findByCompanyName(companyName);
	}

	public Employer findByEmployerId(Long employerId) {
		return employerRepository.findByEmployerId(employerId);
	}

	
	//to find all employer
    public List<Employer> findAllEmployers() {
        return employerRepository.findAll();
    }

	public long countAllEmployers() {
		return employerRepository.count();
	}

	public Employer findByJobPosting_JobId(Long jobId) {
		return employerRepository.findByJobPosting_JobId(jobId);

	}

	public Employer findByEmployerIdAndJobPosting_JobId( Long employer, Long jobId) {
		return employerRepository.findByEmployerIdAndJobPosting_JobId(employer,jobId);

	}

	public Employer findByEmployerIdAndJobId(Long loggedInemployerId, Long jobId) {
		return employerRepository.findByEmployerIdAndJobPosting_JobId(loggedInemployerId,jobId);

	}

	public Employer getByEmployerIdAndJobId(Long loggedInEmployerId, Long jobId) {
		return employerRepository.findByEmployerIdAndJobPosting_JobId(loggedInEmployerId,jobId);
	}

	@Transactional
	public void updateEmployer(ProfileDTO profileDTO, Employer employer) {
		
		employer.setCompanyName(profileDTO.getCompanyName().toLowerCase());
  		employer.setCompanyDescription(profileDTO.getDescription());
  		employer.setAddress(profileDTO.getAddress());  		
  		employer.setWebsite(profileDTO.getWebsite());
  		employer.setNumber(profileDTO.getNumber());
  		
    	employerRepository.save(employer); 
	}

	@Transactional
	public void updateEmployerProfilePicture(Employer employer, MultipartFile companyLogo) {
		String filePath = fileStorageService.saveFile(companyLogo);
		
		employer.setCompanyLogoPath(filePath);
		employerRepository.save(employer); 
		
	}
	
}
