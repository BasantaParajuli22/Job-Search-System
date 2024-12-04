package com.example.springTrain.entity;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.springTrain.enums.Usertype;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
		
	private String username;
	private String password;
	private String email;
		
	@Enumerated(EnumType.STRING)  // Store the enum as a String in the database
	private Usertype usertype;
	
	//one user can have many  notification
	@OneToMany(mappedBy ="users", cascade = CascadeType.ALL)
	@JsonIgnore 
	private List<NotificationMessage> notification;
	
	//this means that Employer and JobSeeker entity class needs to have users 
	@OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	private Employer employer;
	@OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	private JobSeeker jobSeeker;
	
	//private String session;
	
	@CreationTimestamp
	private Instant createdAt;	
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Usertype getUsertype() {
		return usertype;
	}
	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}
	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}
	public List<NotificationMessage> getNotification() {
		return notification;
	}
	public void setNotification(List<NotificationMessage> notification) {
		this.notification = notification;
	}	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	    
	
		
		

}	
