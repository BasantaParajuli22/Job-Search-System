package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.repository.NotificationRepository;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.NotificationMessage;
import com.example.springTrain.table.Users;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	//create notification by user and
	//setting message and unread status and saving
	public NotificationMessage createNotification(Users user, String message) {
		NotificationMessage notification = new NotificationMessage();
		notification.setUsers(user);
		notification.setMessage(message);
		notification.setStatus("not-viewed");
		return notificationRepository.save(notification);
	}
	
	//updating notification status to read
	public NotificationMessage updateNotificationStatus(NotificationMessage notification) {
		notification.setStatus("viewed");
		return notificationRepository.save(notification);
	}
	
	//getting  notification by notificationId 
	public NotificationMessage getNotificationById(Integer notificationId) {
		return notificationRepository.findByNotificationId(notificationId);
	}
	public List<NotificationMessage> getAllnotificationById(Integer userId) {
		return notificationRepository.findAllByUsers_UserId(userId);

		
	}
	//getting all notifications by Users 
	public List<NotificationMessage> getAllNotifications(String username){	
		return notificationRepository.findAllByUsers_Username(username);
	}

	//getting all notifications which is unreaded by user
	public List<NotificationMessage> getAllUnReadedNotifications(String username) {
		return notificationRepository.findAllByUsers_UsernameAndStatus(username,"unread");

	}

	//delete notification individual 
	public void deleteNotification(NotificationMessage notification) {
		 notificationRepository.delete(notification);
	}
	//delete notification all of user
	public void deleteAllNotification(List<NotificationMessage> notification) {
		notificationRepository.deleteAll(notification);
		
	}




	
}
