package com.example.springTrain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.SavedJobsService;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobApplication;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.table.JobSeeker;
import com.example.springTrain.table.SavedJobs;

@Controller
public class JobApplicationController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private SavedJobsService savedJobsService;

	
	//to apply for jobposts of employer
	//jobposts id required
	//jobseekerId required
	//make changes to jobApplication
	@PostMapping("/applications/applyBy/{jobSeekerId}/to/{jobId}/{companyName}")
	public String applyForJobPosts(Model model,
					@PathVariable("jobSeekerId") Integer jobSeekerId,
					@PathVariable("jobId") Integer jobId,
					@PathVariable("companyName") String companyName) {
	    

   	 	// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
       
		JobSeeker loggedJobSeeker = jobSeekerService.findByUsername(username);
		JobSeeker submittedUsername = jobSeekerService.findByJobSeekerId(jobSeekerId);

		if(!loggedJobSeeker.equals(submittedUsername)) {
			logger.warn("Not same jobSeeker so cannot apply");
			return "redirect:/view/jobposts";
		}

		//if employer or jobposting not found // exit //
        Employer employer = employerService.findByCompanyName(companyName); 
        JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
         if(employer == null ||jobPosting == null) {
 			logger.warn("Not same employer or jobId");
            return "redirect:/view/jobposts";
         }  
         //only apply if it hasnot applied 
         //only one jobSeekerId to one jobId
         JobApplication jobApplication = jobApplicationService.getJobSeekerByIdAndJobId(jobSeekerId,jobId); 
         if(jobApplication != null) {
  			logger.warn(" Same JobSeeker cannot apply again to jobpost ");
             return "redirect:/view/jobposts";
          }
         
        jobApplicationService.applyForJobPost(jobId,companyName,jobSeekerId);
    	return "redirect:/view/jobposts";

	}
	
	//Save jobPost by JobSeeker 
	//needs JobSeeker to login
	@PostMapping("/saveBy/jobSeeker/{jobSeekerId}/jobPost/{jobId}")
	public String saveJobsByJobSeeker(Model model,
			@PathVariable("jobSeekerId") Integer jobSeekerId,
			@PathVariable("jobId") Integer jobId) {
		
		// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
		       
		JobSeeker loggedJobSeeker = jobSeekerService.findByUsername(username);
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
		if(!loggedJobSeeker.equals(submittedJobSeeker)) {
			return "redirect:/view/jobposts";
		}

		//ifsubmittedJobSeeker is null then// exit //
         if(submittedJobSeeker == null|| jobPosting == null ) {
   			logger.warn(" jobSeeker not found or jobPost not found to save");
            return "redirect:/view/jobposts";
         }   
         
         SavedJobs alreadySaved= savedJobsService.findBySavedIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);
         if(alreadySaved != null) {
    		logger.info(" JobPost already saved by JobSeeker");
             return "redirect:/view/jobposts";
         }
         
		 savedJobsService.saveJobForJobSeeker(jobPosting,loggedJobSeeker);
		return "redirect:/view/jobposts";
	}
	
}
