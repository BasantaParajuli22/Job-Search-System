package com.example.springTrain.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.user.Employer;
import com.example.springTrain.util.UserAuthorization;

@Controller
public class JobApplyController {

	
	@Autowired
	private JobPostingService jobPostingService;
	
	@Autowired
	private EmployerService employerService;
	
	
	//to create a jobposting 
	//user must be authenticated and should be employer
	@PostMapping("/apply/{jobseeker}/to/{compannyName}")
	public String getJobPostForm(Model model) {
	    
   	 	// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();

        // Find your custom User entity using the username           
        Employer employer = employerService.findByCompanyName(username); // Your custom method to find the User entity
            
         if(employer == null) {
            System.out.println("User not suitable for creating jobposting");
            return "login";
         }
        
    	model.addAttribute("employer", employer);  // Add the employer to the model
    	model.addAttribute("jobPosting", new JobPosting());  // Pass a new JobPosting object
    	return "jobpostform";

	}
}
