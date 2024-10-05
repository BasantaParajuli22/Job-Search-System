package com.example.springTrain.user;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.example.dto.Usertype;

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
public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int user_id;
		
		private String username;
		private String password;
		private String email;
		@Enumerated(EnumType.STRING)
		private Usertype Usertype;
		@CreationTimestamp
		private Instant createdAt;		

		@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	    private Employer employer;

	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
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
//		public String getSesion() {
//			return sesion;
//		}
//		public void setSesion(String sesion) {
//			this.sesion = sesion;
//		}
		public Instant getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Instant createdAt) {
			this.createdAt = createdAt;
		}
		public Usertype getUsertype() {
			return Usertype;
		}
		public void setUserType(Usertype Usertype) {
			this.Usertype = Usertype;
		}
		public int getUser_id() {
			return user_id;
		}
		public void setUserid(int user_id) {
			this.user_id = user_id;
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
	}	
