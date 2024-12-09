package com.example.springTrain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
	   SavedJobs saveJob = new SavedJobs();
	   saveJob.setJobSeeker(jobSeeker);
	   saveJob.setJobPosting(jobPosting);
	   
	   savedJobsRepository.save(saveJob);

	   }

	public void unSaveJobForJobSeeker(JobPosting jobPosting, JobSeeker loggedJobSeeker) {		 
		SavedJobs savedJob = savedJobsRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobPosting.getJobId(),loggedJobSeeker.getJobSeekerId());			
		savedJobsRepository.delete(savedJob);
	}
	
	public SavedJobs findByJobPosting_JobIdAndJobSeekerId(Integer jobId, Integer jobSeekerId) {
		return savedJobsRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);
	}

	public List<SavedJobs> findAllSavedJobsByJobSeeker(JobSeeker loggedinJobSeeker) {
		return savedJobsRepository.findAllSavedJobsByJobSeeker(loggedinJobSeeker);

	}
	
	//return a list of jobApplication deadlines in days
		public List<String> getSavedJobsDeadlines(List<SavedJobs> savedApplicants) {
			
			List<String> dates = new ArrayList<>();
			
			for(SavedJobs job: savedApplicants){
				LocalDate deadline=	job.getJobPosting().getApplicationDeadline();
				if(deadline == null) {
					String message ="no deadline found";
					dates.add(message);	
				}else if(deadline.isBefore(LocalDate.now() )) {
					String message ="-----Time is up";
					dates.add(message);				
				}
				else if(deadline.isAfter(LocalDate.now() )){
					Long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
					String totaldays = "-----" + daysLeft +" days left";
					dates.add(totaldays);
				}
			}		
			return dates; 
		}


		public SavedJobs getJobPostingByJobIdAndJobSekerId(Integer jobId, Integer jobSeekerId) {
			return savedJobsRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);

		}



}
