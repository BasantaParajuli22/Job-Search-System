package com.example.springTrain.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobPostingService;

@Controller
@RequestMapping("/jobposts")
public class JobPostingController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;

	//to create a jobposting 
	//user must be authenticated and should be employer
	@GetMapping("/new/create")
	public String getJobPostForm(Model model) {
	    
   	 	// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
        // Find your custom User entity using the username           
        Employer employer = employerService.findByCompanyName(username); // Your custom method to find the User entity
        // If no employer is found or user is not authorized, redirect to login   
         if(employer == null) {
        	 logger.warn("User not suitable for creating jobposting");
            return "login";
         }

        model.addAttribute("employer", employer);
        model.addAttribute("jobPosting", new JobPosting());  // Pass a new JobPosting object
        return "jobpostform";
	}
	
	//for posting jobpost employerId is needed
	//employerId is set in joposting 
	//and jobposting is created
	@PostMapping("/new/create")
	public String createJobPostForm(@ModelAttribute JobPosting jobPosting,
									@RequestParam("employerId") Integer employerId) {
		
		String username = UserAuthorization.getLoggedInUsername();
	    // Retrieve employer by username
	    Employer employer = employerService.findByCompanyName(username);
	    if (employer == null) {
       	 logger.warn("User not suitable for posting jobposting");
        	return "login";
	    }
	    
		jobPostingService.createJobPosting(jobPosting,employer);//jobposting created
		return "redirect:/view/jobposts";
	}
	
	
	//DELETE 
	//by ALL
	//all jobposting can be deleted by all
    @PostMapping("/{id}/delete")
    public String deleteJobPostingByAdmin(@PathVariable("id") Integer id) {
    	
    	// Fetch the job posting (to ensure it exists)
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting == null) {
       	 logger.warn("User not suitable for deleting jobposting");
        	return "login";
        }
        jobPostingService.deleteJobPosting(jobPosting);
        return "redirect:/view/jobposts";
    }
    
    //deleting a jobPosting By
    //employerid and jobId
    //by an employer only
    @PostMapping("/{jobId}/delete/byemployer")
    public String deleteJobPostingByEmployerId(@PathVariable("jobId") Integer jobId) {
    	    	
    	String username = UserAuthorization.getLoggedInUsername();
        Employer loggedInEmployer = employerService.findByCompanyName(username);  // Assuming employerService is injected
        if(loggedInEmployer == null) {
          	 logger.warn("User not suitable for deleting jobposting");
        	return "login";
        }
        
        Integer employerId = loggedInEmployer.getEmployerId();
        // Fetch the logged-in employerId andjob posting by jobId 
        JobPosting jobPosting = jobPostingService.getJobPostingByEmployerIdAndJobId(employerId, jobId);
        if (jobPosting == null) {
         	 logger.warn("Id didnot match whether the employer or jobpost");
        	return "login";
        }
        
        // Otherwise, proceed with deletion
        jobPostingService.deleteJobPosting(jobPosting);
        return "redirect:/employers/profile";
    	
    }
    
   
    //edit job posts 
	@GetMapping("/{jobId}/edit/by/{companyName}")
	public String getEditForm(
			@PathVariable("jobId") Integer jobId,
			@PathVariable("companyName") String companyName,
			Model model) {

    	String loggedinusername = UserAuthorization.getLoggedInUsername();
    	   		
    	//checking if Form username and jobid can edit the post
    	JobPosting jobPostId = jobPostingService.getJobPostingById(jobId);
    	List<JobPosting> submittedemployer = jobPostingService.findByEmployerCompanyName(companyName); 
    	List<JobPosting> loggedInEmployer = jobPostingService.findByEmployerCompanyName(loggedinusername); 
     
       if( jobPostId == null || submittedemployer == null || loggedInEmployer == null) {
       	 logger.warn("Employer or jobid cannot access to edit jobPost");
    	   return"login";
       }
       if (!submittedemployer.equals(loggedInEmployer)) {
    	   logger.warn("Submitted and loggedin employer doesnot match");
    	   return"login";
       }
       //It prevents users from editing job posts that do not belong to them. 
       //This is crucial for security, as employers should only be able to edit their own job posts.
       // Check if the job being edited matches any of the logged-in employer's job posts
       boolean canEdit = loggedInEmployer.stream()
                           .anyMatch(post -> post.getJobId().equals(jobId));

       if (!canEdit) {
    	   logger.warn("The logged-in user does not have permission to edit job post with ID:" , jobId);
           return "login";
       }
       
       Employer employer = employerService.findByCompanyName(companyName);
       if (employer == null) {
    	   logger.warn("employer cannot edit or not the one");
    	   return "login";
       }
       
		model.addAttribute("employer", employer);  
		model.addAttribute("jobPosting", jobPostId);
		
	    return "jobpostform";  
	}
	
	
	@PostMapping("/{jobId}/edit/by/{companyName}")
	public String postEditForm(@PathVariable("jobId") Integer jobId, @ModelAttribute("jobPosting") JobPosting updatedJobPosting, Model model) {

	    String loggedinusername = UserAuthorization.getLoggedInUsername();
	    
	    JobPosting existingJobPosting = jobPostingService.getJobPostingById(jobId);
	    if (existingJobPosting == null) {
	    	logger.warn("Job post not found for jobId: \" + jobId");
	        return "redirect:/view/jobposts";  
	    }

	    List<JobPosting> loggedInEmployerPosts = jobPostingService.findByEmployerCompanyName(loggedinusername);
	    boolean canEdit = loggedInEmployerPosts.stream().anyMatch(post -> post.getJobId() == jobId);

	    if (!canEdit) {
	    	logger.warn("The logged-in user does not have permission to edit job post with ID: \" + jobId");
	        return "login";  
	    }

	    // Copy updated values from the form (updatedJobPosting) to existingJobPosting
	    existingJobPosting.setTitle(updatedJobPosting.getTitle());
	    existingJobPosting.setJobDescription(updatedJobPosting.getJobDescription());
	    existingJobPosting.setCityLocation(updatedJobPosting.getCityLocation());
	    existingJobPosting.setJobType(updatedJobPosting.getJobType());
	    existingJobPosting.setSalaryRange(updatedJobPosting.getSalaryRange());
	    existingJobPosting.setJobCategory(updatedJobPosting.getJobCategory());
	    existingJobPosting.setRequirements(updatedJobPosting.getRequirements());
	    existingJobPosting.setApplicationDeadline(updatedJobPosting.getApplicationDeadline());
	    existingJobPosting.setExperienceLevel(updatedJobPosting.getExperienceLevel());
	    existingJobPosting.setRemote(updatedJobPosting.isRemote());
	    existingJobPosting.setContactEmail(updatedJobPosting.getContactEmail());


	    // Save the updated job post
	    jobPostingService.updateJobPosting(jobId, existingJobPosting);

	    return "redirect:/view/jobposts";
	}	
}
