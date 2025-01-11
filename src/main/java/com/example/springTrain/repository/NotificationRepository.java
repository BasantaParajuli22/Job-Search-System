package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.NotificationMessage;
import com.example.springTrain.entity.Users;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationMessage,Long> {

	//List<NotificationMessage> findAllByUsers_UserIdAndStatus(Long id, String string);
	//List<NotificationMessage> findAllByUsers_UserId(Long userId);
	List<NotificationMessage> findAllByUsers_UserId(Long userId,Sort sort);
	
	NotificationMessage findByNotificationId(Long notificationId);

	//count notification
	long countByUsers_JobSeekerAndStatus(JobSeeker jobSeeker, String string);
	long countByUsers_EmployerAndStatus(Employer employer, String string);
	
//	long countByUsers_JobSeeker(JobSeeker jobSeeker);
//	long countByUsers_Employer(Employer employer);

	long countByUsersAndStatus(Users user, String string);

}
