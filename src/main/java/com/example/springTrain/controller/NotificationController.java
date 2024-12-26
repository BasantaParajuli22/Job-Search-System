package com.example.springTrain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springTrain.entity.NotificationMessage;
import com.example.springTrain.entity.Users;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.NotificationService;
import com.example.springTrain.service.UsersService;

@Controller
public class NotificationController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsersService userService;
	@Autowired
	private NotificationService notificationService;
	
	
	////get all notification by userId
	@GetMapping("/notification/all")
	public String getAllNotification(Model model) {
		
		String userEmail = UserAuthorization.getLoggedInUserEmail();
		Users loggedInUser = userService.findByEmail(userEmail);
		
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
		
		String userEmail = UserAuthorization.getLoggedInUserEmail();	
		Users loggedInUser = userService.findByEmail(userEmail);
		NotificationMessage notification = notificationService.getNotificationById(notificationId);
		
		if(loggedInUser == null || notification == null) {	
        	logger.warn("user or notification not found to change status");
        	return"login";
		}
		notificationService.updateNotificationStatus(notification); 
		return "redirect:/notification/all";
	}

		//clear one notification
		@PostMapping("/notification/delete/{notificationId}")
		public String postDeleteSpecificNotification(Model model,
				@PathVariable("notificationId")Integer notificationId) {
			
			String userEmail = UserAuthorization.getLoggedInUserEmail();	
			Users loggedInUser = userService.findByEmail(userEmail);
			NotificationMessage notification = notificationService.getNotificationById(notificationId);
			
			if(loggedInUser == null || notification == null) {	
	        	logger.warn("user or notification not found to delete notification");
	        	return"login";
			}

			notificationService.deleteNotification(notification); 
			return "redirect:/notification/all";
		}

		//delete all notification of userId
		@PostMapping("/notification/deleteAllOf/{userId}")
		public String postDeleteAllNotification(Model model,
				@PathVariable("userId")Integer userId) {
			
			String userEmail = UserAuthorization.getLoggedInUserEmail();	
			Users loggedInUser = userService.findByEmail(userEmail);
			List<NotificationMessage> notification = notificationService.getAllNotificationsByUserId(userId);
			
			if(loggedInUser == null || notification == null) {	
	        	logger.warn("User not found to change status");
	        	return"login";
			}
			
			notificationService.deleteAllNotification(notification); 
			return "redirect:/notification/all";
		}
}
