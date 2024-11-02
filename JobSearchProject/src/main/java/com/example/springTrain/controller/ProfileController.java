package com.example.springTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.AdminService;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.table.Admin;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobSeeker;


@Controller
public class ProfileController{
	
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private AdminService adminService;

	
	//to visit jobseekers profile
    @GetMapping("/jobseekers/profile")
    public String getJobseekerProfile(Model model) {
        
    	// Get the currently logged-in user's username
    	String loggedinjobSeeker = UserAuthorization.getLoggedInJobSeekerUsername();
        System.out.println(loggedinjobSeeker);
       JobSeeker jobSeeker = jobSeekerService.findByUsername(loggedinjobSeeker);
       if(loggedinjobSeeker == null || jobSeeker == null) {
    	   return "login";
       }
       
        model.addAttribute("jobSeeker", jobSeeker);
        return "jobseeker-profile";
    }
	
	//to visit employers profile
	@GetMapping("/employers/profile")
	public String getEmployerProfile(Model model) {
		
		// Get the currently logged-in user's username
		String loggedinEmployer = UserAuthorization.getLoggedInUsername();
        System.out.println(loggedinEmployer);

		Employer employer = employerService.findByCompanyName(loggedinEmployer);
		if(loggedinEmployer == null || employer == null) {
	    	   return "login";
	    }
		
		model.addAttribute("employer", employer);  // Add the employer to the model
		return "employer-profile";
		
	}
	
	//to visit admin profile
	@GetMapping("/admin/profile")
	public String getAdminProfile(Model model) {
		
		// Get the currently logged-in user's username
		String loggedinusername = UserAuthorization.getLoggedInUsername();
		Admin admin = adminService.findByUsername(loggedinusername);
		if (loggedinusername == null || admin == null) {
			return "login";
		}
		
		model.addAttribute("admin", admin);  // Add the admin to the model
		return "admin-profile";
		
	}
}