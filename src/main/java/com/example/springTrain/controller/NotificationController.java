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

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.NotificationMessage;
import com.example.springTrain.entity.Users;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.NotificationService;
import com.example.springTrain.service.UsersService;

@Controller
public class NotificationController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsersService userService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobSeekerService jobSeekerService;

    @ModelAttribute
    public void addJobSeekerToModel(Model model) {
    	//getting LoggedInJobSeekerUsername in string
        String username = UserAuthorization.getLoggedInJobSeekerUsername();
        if (username != null) {//if found finding in jobSeeker entity
            JobSeeker jobSeeker = jobSeekerService.findByUsername(username);
            if (jobSeeker != null) {//if found adding to model
                model.addAttribute("jobSeeker", jobSeeker);
            } 
        }
    }
    
    @ModelAttribute
    public void addEmployerToModel(Model model) {
    	//getting loggedinEmployerUsername in string
        String username = UserAuthorization.getLoggedInEmployerUsername();
        if (username != null) {//if found finding in employer entity
            Employer employer = employerService.findByCompanyName(username);
            if (employer != null) {//if found adding to model
                model.addAttribute("employer", employer);
            } 
        }
    }
	////get all notification by userId
	@GetMapping("/notification/all")
	public String getAllNotification(Model model) {
		String username = UserAuthorization.getLoggedInUsername();
		
		Users loggedInUser = userService.findByUsername(username);
		if(loggedInUser == null) {	
        	logger.warn("User not found to show notifiation");
        	return"login";
		}
		Integer userId = loggedInUser.getUserId();
		List<NotificationMessage> allNotification = notificationService.getAllNotificationsByUserId(userId); 
		model.addAttribute("allNotification",allNotification);
		model.addAttribute("userId",userId);
		return "notification";
	}

	//change its notification status to read individually 
	@PostMapping("/notification/read/{notificationId}")
	public String postReadedNotification(Model model,
			@PathVariable("notificationId")Integer notificationId) {
		
		String username = UserAuthorization.getLoggedInUsername();
		Users loggedInUser = userService.findByUsername(username);
		if(loggedInUser == null) {	
        	logger.warn("User not found to change status");
        	return"login";
		}
		NotificationMessage notification = notificationService.getNotificationById(notificationId);
		if(notification == null) {
			logger.warn("notification id not found to read");
			return "redirect:/";
		}
		notificationService.updateNotificationStatus(notification); 
		return "redirect:/notification/all";
	}

		//clear one notification
		@PostMapping("/notification/delete/{notificationId}")
		public String postDeleteSpecificNotification(Model model,
				@PathVariable("notificationId")Integer notificationId) {
			
			String username = UserAuthorization.getLoggedInUsername();
			Users loggedInUser = userService.findByUsername(username);
			if(loggedInUser == null) {	
	        	logger.warn("User not found to change status");
	        	return"login";
			}
			NotificationMessage notification = notificationService.getNotificationById(notificationId);
			if(notification == null) {
				logger.warn("notification id not found to read");
				return "redirect:/";
			}
			notificationService.deleteNotification(notification); 
			return "redirect:/notification/all";
		}

		//delete all notification of userId
		@PostMapping("/notification/deleteAllOf/{userId}")
		public String postDeleteAllNotification(Model model,
				@PathVariable("userId")Integer userId) {
			
			String username = UserAuthorization.getLoggedInUsername();
			Users loggedInUser = userService.findByUsername(username);
			if(loggedInUser == null) {	
	        	logger.warn("User not found to change status");
	        	return"login";
			}
			List<NotificationMessage> notification = notificationService.getAllNotificationsByUserId(userId);
			if(notification == null) {
				logger.warn("notification id not found to read");
				return "redirect:/";
			}
			notificationService.deleteAllNotification(notification); 
			return "redirect:/notification/all";
		}
}