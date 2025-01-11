package com.example.springTrain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.JobApplication;
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
	
	public SavedJobs findByJobPosting_JobIdAndJobSeekerId(Long jobId, Long jobSeekerId) {
		return savedJobsRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);
	}

	public List<SavedJobs> findAllSavedJobsByJobSeeker(JobSeeker loggedinJobSeeker) {
		return savedJobsRepository.findAllSavedJobsByJobSeeker(loggedinJobSeeker);

	}
	
	//return a list of jobApplication deadlines in days
	public List<String> getSavedJobsDeadlines(List<SavedJobs> allSavedJobs) {
        List<String> dates = new ArrayList<>();
        
        for (SavedJobs job : allSavedJobs) {
            
            if(job == null) {  
                 dates.add("No Deadline Found");
                 continue;
            }
            if(job.getJobPosting() == null) {    
                dates.add("No Deadline Found");
                continue;
            }
            
             LocalDate deadline = job.getJobPosting().getApplicationDeadline();
              
           if (deadline == null) {
                dates.add("No Deadline Set");
                
            } else if (deadline.isBefore(LocalDate.now())) {    
                dates.add("Time is up");
                
            } else {
                 
                Long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
                String totaldays = String.format("%d days", daysLeft); // Use String.format
                dates.add(totaldays);
            }
        }
        return dates;
    }


		public SavedJobs getJobPostingByJobIdAndJobSekerId(Long jobId, Long jobSeekerId) {
			return savedJobsRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);

		}



}
