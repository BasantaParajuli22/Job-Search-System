package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.NotificationMessage;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationMessage,Integer> {

	//List<NotificationMessage> findAllByUsers_Username(String username);

	List<NotificationMessage> findAllByUsers_UsernameAndStatus(String loggedInUsername, String string);
	NotificationMessage findByNotificationId(Integer notificationId);

	List<NotificationMessage> findAllByUsers_UserId(Integer userId);



}
