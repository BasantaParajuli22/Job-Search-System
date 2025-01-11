package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springTrain.dto.ProfileDTO;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.JobSeekerRepository;

@Service
public class JobSeekerService {
	
	private final JobSeekerRepository jobSeekerRepository;
	private final FileStorageService fileStorageService;
	
	@Autowired
	public JobSeekerService(JobSeekerRepository jobSeekerRepository,
			FileStorageService fileStorageService) {
		this.jobSeekerRepository = jobSeekerRepository;
		this.fileStorageService =fileStorageService;
	}
	
	//to find user from repository of JobSeeker
	public JobSeeker findByUsers(Users user) {
		return jobSeekerRepository.findByUsers(user);
	}

	//to find all jobSeekers
	public List<JobSeeker> findAllJobSeekers() {
		return jobSeekerRepository.findAll();
	}

	public JobSeeker findByJobSeekerId(Long jobseekerId) {
		return jobSeekerRepository.findByJobSeekerId(jobseekerId);

	}
	public JobSeeker findIdByJobSeekerId(Long jobseekerId) {
		return jobSeekerRepository.findByJobSeekerId(jobseekerId);

	}

	public long countAlljobSeekers() {
		return jobSeekerRepository.count();
	}


	@Transactional
	public void updateJobSeeker(ProfileDTO profileDTO, JobSeeker jobSeeker) {

		jobSeeker.setFullName(profileDTO.getFullName());
    	jobSeeker.setNumber(profileDTO.getNumber());
 		jobSeeker.setSkills(profileDTO.getSkills());
 		jobSeeker.setDescription(profileDTO.getDescription());
 		
        jobSeekerRepository.save(jobSeeker); 
	}
	
	@Transactional
	public void updateJobSeekerProfilePicture(JobSeeker jobSeeker, MultipartFile imageFile) {
		String imageFilePath = fileStorageService.saveFile(imageFile);
		
		jobSeeker.setProfilePicturePath(imageFilePath);
    	jobSeekerRepository.save(jobSeeker); 
		
	}
	
	@Transactional
	public void updateJobSeekerProfileResume(JobSeeker jobSeeker, MultipartFile file) {
		String filePath = fileStorageService.saveFile(file);
		
		jobSeeker.setResumePath(filePath);
    	jobSeekerRepository.save(jobSeeker); 
	}

}
