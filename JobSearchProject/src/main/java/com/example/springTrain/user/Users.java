package com.example.springTrain.user;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import com.example.springTrain.dto.Usertype;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int userId;
		
		private String username;
		private String password;
		private String email;
		
		// Constructor for creating a Users object
		
		@Enumerated(EnumType.STRING)  // Store the enum as a String in the database
		private Usertype usertype;
		
		@CreationTimestamp
		private Instant createdAt;		

		//this means that Employer and JobSeeker class needs to have users 
		@OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	    private Employer employer;
	    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	    private JobSeeker jobSeeker;
	    
		//private String session;
		
		
		//getters and setters
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Instant getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Instant createdAt) {
			this.createdAt = createdAt;
		}
		public Usertype getUsertype() {
			return usertype;
		}
		public void setUserType(Usertype usertype) {
			this.usertype = usertype;
		}
		public JobSeeker getJobSeeker() {
			return jobSeeker;
		}
		public void setJobSeeker(JobSeeker jobSeeker) {
			this.jobSeeker = jobSeeker;
		}
		public Employer getEmployer() {
			return employer;
		}
		public void setEmployer(Employer employer) {
			this.employer = employer;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
	}	
