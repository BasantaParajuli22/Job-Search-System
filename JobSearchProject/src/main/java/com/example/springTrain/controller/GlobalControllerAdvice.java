package com.example.springTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.NotificationService;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobSeekerService jobSeekerService;

	
	@ModelAttribute
	public void addUserToModel(Model model) {
	    String jobSeekerEmail = UserAuthorization.getLoggedInJobSeekerEmail();
	    if (jobSeekerEmail != null) {
	        JobSeeker jobSeeker = jobSeekerService.findByEmail(jobSeekerEmail);
	        if (jobSeeker != null) {	
	            long notificationCount = notificationService.countUnreadNotificationsOfjobSeeker(jobSeeker);
	    		model.addAttribute("notificationCount",notificationCount);
	    		model.addAttribute("jobSeeker", jobSeeker);

	        }
	    }

	    String employerEmail = UserAuthorization.getLoggedInEmployerEmail();
	    if (employerEmail != null) {
	        Employer employer = employerService.findByEmail(employerEmail);
	        if (employer != null) {       	
	            long notificationCount = notificationService.countUnreadNotificationsOfEmployer(employer);
	    		model.addAttribute("notificationCount",notificationCount);
	    		model.addAttribute("employer", employer);
	    		
	        }
	    }
	}
}
