package com.example.springTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.NotificationService;
import com.example.springTrain.service.UsersService;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UsersService userService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private EmployerService employerService;
	
	
	
	@ModelAttribute
	public void addUserToModel(Model model) {
	    String email = UserAuthorization.getLoggedInUserEmail();
	    Users user = userService.findByEmail(email);
	    
	    
	    if (email != null || user != null) {
	    	String userRole = UserAuthorization.getLoggedInUserRole();
	    	
	    	if("ROLE_JOBSEEKER".equals(userRole)) {
	    		JobSeeker jobSeeker = jobSeekerService.findByUsers(user);
	    		
	    		 if( jobSeeker != null) {
	    		  	long notificationCount = notificationService.countUnreadNotificationsOfjobSeeker(jobSeeker);
	    		    model.addAttribute("notificationCount",notificationCount);
	    		    model.addAttribute("jobSeeker", jobSeeker); 	
	    		}
	    	}
	    	
	    	if("ROLE_EMPLOYER".equals(userRole)) {
	    		Employer employer = employerService.findByUser(user);
	    		    
	    		if(employer != null) {	    		    	
	    		    long notificationCount = notificationService.countUnreadNotificationsOfEmployer(employer);
	    		    model.addAttribute("notificationCount",notificationCount);
	    		    model.addAttribute("employer", employer);
	    		    	
	    		}
	    	}
	    	
	    }
	}
}
