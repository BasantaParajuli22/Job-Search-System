package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.NotificationMessage;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationMessage,Integer> {

	//List<NotificationMessage> findAllByUsers_UserIdAndStatus(Integer id, String string);
	List<NotificationMessage> findAllByUsers_UserId(Integer userId);
	
	NotificationMessage findByNotificationId(Integer notificationId);

	
	//count notification
	long countByUsers_JobSeekerAndStatus(JobSeeker jobSeeker, String string);
	long countByUsers_EmployerAndStatus(Employer employer, String string);
	
	long countByUsers_JobSeeker(JobSeeker jobSeeker);
	long countByUsers_Employer(Employer employer);

}
