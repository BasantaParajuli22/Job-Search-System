package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.NotificationMessage;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.NotificationRepository;

@Service
public class NotificationService {
	
	private NotificationRepository notificationRepository;

	@Autowired
	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
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
	//getting all notifications by Users 
	public List<NotificationMessage> getAllNotificationsByUserId(Integer userId){	
		return notificationRepository.findAllByUsers_UserId(userId);
	}
	
	//delete notification individual 
	public void deleteNotification(NotificationMessage notification) {
		 notificationRepository.delete(notification);
	}
	//delete notification all of user
	public void deleteAllNotification(List<NotificationMessage> notification) {
		notificationRepository.deleteAll(notification);
	}

	public long countUnreadNotifications(Users user) {
		return notificationRepository.countByUsersAndStatus(user, "not-viewed");
	}

	//count notifications by unread 
	public long countUnreadNotificationsOfjobSeeker(JobSeeker jobSeeker) {
		return notificationRepository.countByUsers_JobSeekerAndStatus(jobSeeker, "not-viewed");
	}	
	public long countUnreadNotificationsOfEmployer(Employer employer) {
		return notificationRepository.countByUsers_EmployerAndStatus(employer, "not-viewed");
	}
	
//	//count notifications all 
//	public long countNotificationsOfjobSeeker(JobSeeker jobSeeker) {
//		return notificationRepository.countByUsers_JobSeeker(jobSeeker);
//	}	
//	public long countNotificationsOfEmployer(Employer employer) {
//		return notificationRepository.countByUsers_Employer(employer);
//	}
	
	
	
}
