package com.example.springTrain.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.table.JobApplication;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.user.Employer;
import com.example.springTrain.util.UserAuthorization;

@Controller
public class JobApplicationController {

	
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobApplicationService jobApplicationService;
	
	
	//to apply for jobposts of employer
	//jobposts id required
	//jobseekerId required
	//make changes to application
	
	@PostMapping("/applications/applyBy/{jobSeekerId}/to/{jobId}/{companyName}")
	public String applyForJobPosts(Model model,
					@PathVariable("jobSeekerId") Integer jobSeekerId,
					@PathVariable("jobId") Integer jobId,
					@PathVariable("companyName") String companyName) {
	    
		System.out.println("jobApplicationController");
		System.out.println("");
		 System.out.println("Submitting application for Job ID: " + jobId);
		   System.out.println("Job Seeker ID: " + jobSeekerId);
		   System.out.println("Company Name: " + companyName);

   	 	// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
		   System.out.println("Logged in username: " + username);

		//if employer or jobposting not found // exit //
        Employer employer = employerService.findByCompanyName(companyName); 
         if(employer == null) {
            System.out.println("compannyName not found");
            return "redirect:/view/jobposts";
         }  
         
         JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
         if(jobPosting == null) {
             System.out.println("JobPost not available");
             return "redirect:/view/jobposts";
          }
         
         //only apply if it hasnot applied 
         //only one jobSeekerId to one jobId
         JobApplication jobApplication = jobApplicationService.getJobSeekerByIdAndJobId(jobSeekerId,jobId); 
         if(jobApplication != null) {
             System.out.println("JobSeeker cannot apply again to jobpost" +jobSeekerId+jobId);
             System.out.println();
             return "redirect:/view/jobposts";
          }
         
        jobApplicationService.applyForJobPost(jobId,companyName,jobSeekerId);
    	return "redirect:/view/jobposts";

	}
	
}
