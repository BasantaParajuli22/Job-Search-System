package com.example.springTrain.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.user.Employer;
import com.example.springTrain.user.JobSeeker;


@RequestMapping("/view")
@Controller
public class ViewController {

	
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobSeekerService jobSeekerService;
	
	
	//Dashboard which displays all data
	@GetMapping("/dashboard")
	public String dashBoard(Model model) {
		
		 List<JobPosting> jobPostings = jobPostingService.findAllJobPostings();
		 List<Employer> employers = employerService.findAllEmployers();
		 List<JobSeeker> jobseekers = jobSeekerService.findAllJobSeekers();

		 System.out.println("JobPostings::" + jobPostings); 
		 System.out.println("Employers::::" + employers); 
		 System.out.println("jobseekers:::" + jobseekers); 

		 model.addAttribute("jobPostings",jobPostings); 
		 model.addAttribute("employers",employers);    
		 model.addAttribute("jobseekers",jobseekers);    

		 return "dashboard";
	}
	
	//display all lists of employers
	@GetMapping("employers")
	public String listAllEmployers(Model model) {
		 List<Employer> employers = employerService.findAllEmployers();
		 model.addAttribute("jobPostings",employers); 
		return "employerslist";
	}
	
	//display all lists of jobseekers
	@GetMapping("/jobseekers")
	public String listAllJobseekers(Model model) {
		 List<JobSeeker> jobseekers = jobSeekerService.findAllJobSeekers();
		 model.addAttribute("jobseekers",jobseekers);	        
		return "jobseekerslist";
	}
	
	//view all jobpostings without restrictions
	@GetMapping("/jobposts")
	public String listAllJobPostings(Model model) {
		 List<JobPosting> jobPostings = jobPostingService.findAllJobPostings();		    
		 model.addAttribute("jobPostings",jobPostings);    
		 return "jobpost";
	}
	
	//view jobposting of specific jobpost
	//recieves and uses id of jobposting 
	//finds the object of the id which is only one
	//or sends jobposting not found
	@PostMapping("/jobposts/{jobId}")
	public String listAllEmployerJobPostings(Model model,
			@PathVariable("jobId") int jobId) {
		
		// Fetch the job post by ID
	    JobPosting jobPost = jobPostingService.getJobPostingById(jobId);
	    
	    // Fetch related jobs, for example by category or employer
		 //List<JobPosting> relatedJobs = jobPostingService.findRelatedJobPostings(jobPost.getCategory(), jobPost.getEmployer().getUsers().getUserId());		 
		// Add the job details to the model
		    model.addAttribute("job", jobPost);
		    System.out.println("Job Title: " + jobPost.getTitle());

		    //model.addAttribute("relatedJobs", relatedJobs);  
		    return "jobListing";  // Thymeleaf template
	}
	
	//view a certain jobseeker details for searching purposes
	
	
	
	
	
	//view jobposting of specific employer 
	//recieves and uses id of employer id  
	//finds the list of jobpostings from id
	//or sends jobposting not found 
	
	//view a certain employer details for searching purposes
	


}
