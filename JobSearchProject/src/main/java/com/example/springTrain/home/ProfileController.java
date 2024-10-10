package com.example.springTrain.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.userdetails.User;

import com.example.springTrain.user.Admin;
import com.example.springTrain.user.Employer;
import com.example.springTrain.user.JobSeeker;
import com.example.springTrain.service.AdminService;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.UserService;


@Controller
public class ProfileController{
	
	private final JobSeekerService jobSeekerService;
	private final UserService userService;
	private final EmployerService employerService;
	private final AdminService adminService;

    @Autowired
    public ProfileController(JobSeekerService jobSeekerService, EmployerService employerService, AdminService adminService,UserService userService) {
        this.jobSeekerService = jobSeekerService;
        this.employerService = employerService;
        this.adminService = adminService;
        this.userService = userService;
    }
    
    
    @GetMapping("/jobseeker/profile")
    public String getJobseekerProfile(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User secureUser) {
        
        if (secureUser == null) {
            throw new RuntimeException("User is not authenticated");
        }
        
        // Get the username from the security context //from Authenticated 
        String username = secureUser.getUsername();
        System.out.println("Authenticated username: " + username);
        
        // Find your custom User entity using the username
       com.example.springTrain.user.User user = userService.findByUsername(username); // Your custom method to find the User entity
        
        // Now that you have the custom User entity, find the associated JobSeeker
        JobSeeker jobSeeker = jobSeekerService.findByUser(user);
        
        // Add the JobSeeker to the model to pass it to the view
        model.addAttribute("jobSeeker", jobSeeker);
        
        return "jobseeker-profile";
    }
	
	@GetMapping("/employer/profile")
	public String getEmployerProfile(Model model,@AuthenticationPrincipal org.springframework.security.core.userdetails.User secureUser ) {
		
		if (secureUser == null) {
            throw new RuntimeException("User is not authenticated");
        }
        
        // Get the username from the security context //from Authenticated 
        String username = secureUser.getUsername();
        System.out.println("Authenticated username: " + username);
        
        // Find your custom User entity using the username
       com.example.springTrain.user.User user = userService.findByUsername(username); // Your custom method to find the User entity
        
        // Now that you have the custom User entity, find the associated Employer
		Employer employer = employerService.findByUser(user);
		model.addAttribute("employer", employer);  // Add the employer to the model

		return "employer-profile";
		
	}
	
	@GetMapping("/admin/profile")
	public String getAdminProfile(Model model,@AuthenticationPrincipal org.springframework.security.core.userdetails.User secureUser ) {
		
		if (secureUser == null) {
            throw new RuntimeException("User is not authenticated");
        }
        
        // Get the username from the security context //from Authenticated 
        String username = secureUser.getUsername();
        System.out.println("Authenticated username: " + username);
        
        // Find your custom User entity using the username
       com.example.springTrain.user.User user = userService.findByUsername(username); // Your custom method to find the User entity
        
		Admin admin = adminService.findByUser(user);
		model.addAttribute("admin", admin);  // Add the jobseeker to the model

		return "admin-profile";
		
	}
	
}