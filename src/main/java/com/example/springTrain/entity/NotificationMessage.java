package com.example.springTrain.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "notification")
public class NotificationMessage{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;
	
	@ManyToOne
	@JsonIgnore 
	@JoinColumn(name ="userId",referencedColumnName ="userId", nullable = false)
	private Users users;
	
	private String message;
	private String status;//read or no read
	
	@CreationTimestamp
	private Instant messageAt;
	
	
	public Long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getMessageAt() {
		return messageAt;
	}
	public void setMessageAt(Instant messageAt) {
		this.messageAt = messageAt;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	
}
