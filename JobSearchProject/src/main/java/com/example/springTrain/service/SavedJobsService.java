package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;
import com.example.springTrain.repository.SavedJobsRepository;

@Service
public class SavedJobsService  {
	
	 private SavedJobsRepository savedJobsRepository;

	@Autowired
	public SavedJobsService(SavedJobsRepository savedJobsRepository) {
		this.savedJobsRepository = savedJobsRepository;
	}

	//save job by jobSeeker
	public void saveJobForJobSeeker(JobPosting jobPosting, JobSeeker jobSeeker) {
	   	
		System.out.println(jobPosting);
		System.out.println(jobSeeker);

	   SavedJobs saveJob = new SavedJobs();
	   saveJob.setJobSeeker(jobSeeker);
	    saveJob.setJobPosting(jobPosting);
	
	    savedJobsRepository.save(saveJob);

	   }

	public SavedJobs findBySavedIdAndJobSeeker_JobSeekerId(Integer jobId, Integer jobSeekerId) {
		return savedJobsRepository.findBySavedIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);
	}

	public List<SavedJobs> findAllSavedJobsByJobSeeker(JobSeeker loggedinJobSeeker) {
		return savedJobsRepository.findAllSavedJobsByJobSeeker(loggedinJobSeeker);

	}

}
