package com.example.springTrain.home;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobCategoryService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobCategory;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.util.UserAuthorization;

@Controller
@RequestMapping("/jobposts")
public class JobPostingController {
	
	@Autowired
	private JobPostingService jobPostingService;
	
	@Autowired
	private EmployerService employerService;
	
	@Autowired
	private JobCategoryService jobCategoryService;
	
	
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
            System.out.println("User not suitable for creating jobposting");
            return "login";
         }
        List<JobCategory> categories = jobCategoryService.getAllCategories();
        if(categories == null) {
            System.out.println("categories, data.sql may not has been exceuted");
            return "login";
         }
        model.addAttribute("categories", categories);
        model.addAttribute("employer", employer);  // Add the employer to the model
        model.addAttribute("jobPosting", new JobPosting());  // Pass a new JobPosting object
        return "jobpostform";
	}
	
	//for posting jobpost employerId is needed
	//employerId is set in joposting 
	//and jobposting is created
	@PostMapping("/new/create")
	public String createJobPostForm(@ModelAttribute JobPosting jobPosting,
									@RequestParam("employerId") Integer employerId) {
		
   	 	// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
	    // Retrieve employer by username
	    Employer employer = employerService.findByCompanyName(username);
	    // If employer not found, redirect to login
	    if (employer == null) {
	    	System.out.println("User not suitable for posting jobposting");
        	return "login";
	    }
        System.out.println("Creating job posting:in postmethod " + jobPosting);
		jobPostingService.createJobPosting(jobPosting,employer);//jobposting created
		return "redirect:/view/jobposts";//to list all jobs after posting
	}
	
	
	//DELETE 
	//by ALL
	//all jobposting can be deleted by all
    @PostMapping("/{id}/delete")
    public String deleteJobPostingByAdmin(@PathVariable("id") Integer id) {
    	
    	// Fetch the job posting (to ensure it exists)
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting == null) {
            System.out.println("User not suitable for deleting jobposting");
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
    	    	
    	 // Get the currently logged-in user's username
    	String username = UserAuthorization.getLoggedInUsername();
        // Fetch the Employer entity using the username
        Employer loggedInEmployer = employerService.findByCompanyName(username);  // Assuming employerService is injected
        if(loggedInEmployer == null) {
        	System.out.println(loggedInEmployer);
        	return "login";
        }
        int employerId = loggedInEmployer.getEmployerId();

        if(employerId == 0 || loggedInEmployer == null) {
            System.out.println("Employer ID: " + employerId);
            System.out.println("Not a logged in employer"+loggedInEmployer);
            return "login";
        }
        // Fetch the job posting by jobId and the logged-in employerId
        JobPosting jobPosting = jobPostingService.getJobPostingByEmployerIdAndJobId(employerId, jobId);
        if (jobPosting == null) {
            System.out.println("Id didnot match whether the employer or jobpost");
        	return "login";
        }
        
        // Otherwise, proceed with deletion
        jobPostingService.deleteJobPosting(jobPosting);
        return "redirect:/view/jobposts";
    	
    }
    
    
    //not tested//
    //test result
    //doesnot edit instead creates a new one using previous values  
    
    //edit job posts 
	@GetMapping("/{jobId}/edit/by/{companyName}")
	public String getEditForm(
			@PathVariable("jobId") Integer jobId,
			@PathVariable("companyName") String companyName,
			Model model) {

		 // Get the currently logged-in user's username
		//can be any username
    	String loggedinusername = UserAuthorization.getLoggedInUsername();
		
    	//if loggedin is employer  or jobposting is null??
    	//we cannot edit then
    	   		
    	//checking if Form username and jobid can edit the post
    	JobPosting jobPostById = jobPostingService.getJobPostingById(jobId);
    	List<JobPosting> jobPostByCompany = jobPostingService.findByEmployerCompanyName(companyName); 

    	List<JobPosting> loggedInEmployerPosts = jobPostingService.findByEmployerCompanyName(loggedinusername); 
       //String empName = loggedinEmployer.getEmployer();//finding loggedin employer username
       //int jobPostId = loggedinEmployer.getJobId();//finding loggedin employer id
              
       if( jobPostById == null || jobPostByCompany == null || loggedInEmployerPosts == null) {
    	   System.out.println("Employer or jobid is null");
    	   return"login";
       }
       //It prevents users from editing job posts that do not belong to them. 
       //This is crucial for security, as employers should only be able to edit their own job posts.
       // Check if the job being edited matches any of the logged-in employer's job posts
       boolean canEdit = loggedInEmployerPosts.stream()
                           .anyMatch(post -> post.getJobId() == jobId);

       if (!canEdit) {
           System.out.println("The logged-in user does not have permission to edit job post with ID: \" + jobId");
           return "login";
       }
       // Fetch employer details if it exists
       Employer employer = employerService.findByCompanyName(companyName);
       if (employer == null) {
    	   System.out.println("employer cannot edit or not the one");
       }
    // Proceed with the job post update if all checks pass 
       List<JobCategory> categories = jobCategoryService.getAllCategories();
       model.addAttribute("employer", employer);  

       model.addAttribute("categories", categories);
       model.addAttribute("jobPosting", jobPostById);
	    return "jobpostform";  // Name of your Thymeleaf template
	}
	
	
	@PostMapping("/{jobId}/edit/by/{companyName}")
	public String postEditForm(@PathVariable("jobId") Integer jobId, @ModelAttribute("jobPosting") JobPosting updatedJobPosting, Model model) {

	    String loggedinusername = UserAuthorization.getLoggedInUsername();
	    
	    JobPosting existingJobPosting = jobPostingService.getJobPostingById(jobId);
	    if (existingJobPosting == null) {
	        System.out.println("Job post not found for jobId: " + jobId);
	        return "redirect:/view/jobposts";  // or some error page
	    }

	    List<JobPosting> loggedInEmployerPosts = jobPostingService.findByEmployerCompanyName(loggedinusername);
	    boolean canEdit = loggedInEmployerPosts.stream().anyMatch(post -> post.getJobId() == jobId);

	    if (!canEdit) {
	        System.out.println("The logged-in user does not have permission to edit job post with ID: " + jobId);
	        return "login";  // Redirect to login or error page
	    }

	    // Copy updated values from the form (updatedJobPosting) to existingJobPosting
	    existingJobPosting.setTitle(updatedJobPosting.getTitle());
	    existingJobPosting.setJobDescription(updatedJobPosting.getJobDescription());
	    existingJobPosting.setLocation(updatedJobPosting.getLocation());
	    existingJobPosting.setJobType(updatedJobPosting.getJobType());
	    existingJobPosting.setSalaryRange(updatedJobPosting.getSalaryRange());
	    existingJobPosting.setJobCategory(updatedJobPosting.getJobCategory());
	    existingJobPosting.setRequirements(updatedJobPosting.getRequirements());
	    existingJobPosting.setStatus(updatedJobPosting.getStatus());
	    existingJobPosting.setApplicationDeadline(updatedJobPosting.getApplicationDeadline());
	    existingJobPosting.setExperienceLevel(updatedJobPosting.getExperienceLevel());
	    existingJobPosting.setRemote(updatedJobPosting.isRemote());
	    existingJobPosting.setStartDate(updatedJobPosting.getStartDate());
	    existingJobPosting.setEndDate(updatedJobPosting.getEndDate());
	    existingJobPosting.setContactEmail(updatedJobPosting.getContactEmail());

	    // Save the updated job post
	    jobPostingService.updateJobPosting(jobId, existingJobPosting);

	    return "redirect:/view/jobposts";
	}	
}
