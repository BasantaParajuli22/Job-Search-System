//package com.example.springTrain.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.springTrain.entity.NotificationMessage;
//import com.example.springTrain.entity.Users;
//import com.example.springTrain.security.UserAuthorization;
//import com.example.springTrain.service.EmployerService;
//import com.example.springTrain.service.JobSeekerService;
//import com.example.springTrain.service.NotificationService;
//import com.example.springTrain.service.UsersService;
//
//
//@RestController
//public class NotificationRestController {
//
//	@Autowired
//	private EmployerService employerService;
//	@Autowired
//	private JobSeekerService jobSeekerService;
//
//	Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	private UsersService userService;
//	@Autowired
//	private NotificationService notificationService;
//
//	////get all notification by userId
//	@GetMapping("api/notification/all")
//	public ResponseEntity<?> getAllRestNotification(Model model) {
//		
//		String username = UserAuthorization.getLoggedInUsername();	
//		Users loggedInUser = userService.findByUsername(username);
//		if(loggedInUser == null) {	
//        	logger.warn("User not found to show notifiation");
//        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//        			.body("User not found Error");
//		}
//		
//		Integer userId = loggedInUser.getUserId();
//		List<NotificationMessage> allNotification = notificationService.getAllNotificationsByUserId(userId); 
//		
//		if(allNotification.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT)
//					.body("no notifications found ");
//		}
//		return ResponseEntity.ok(allNotification);	
//		
//	}
//	
//}
