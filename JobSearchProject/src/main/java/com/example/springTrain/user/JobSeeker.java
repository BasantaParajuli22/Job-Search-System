package com.example.springTrain.user;


import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;

import com.example.springTrain.table.ApplicationTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

	@Entity
	@Table(name = "jobSeekers")
	public class JobSeeker {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int jobSeekerId;
		
		@OneToOne
		@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
		private Users users;
		
		@OneToOne(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
	    private ApplicationTable applicationTable;

		private String email;
		private String number;
		private String address;
		private String skills;//skills will be in list//
//		private String resume;

		@CreationTimestamp
		private Instant createdAt;
		
		//getters and setters
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		} 
//		public String getResume() {
//			return resume;
//		}
//		public void setResume(String resume) {
//			this.resume = resume;
//		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getSkills() {
			return skills;
		}
		public void setSkills(String skills) {
			this.skills = skills;
		}
		public Instant getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Instant createdAt) {
			this.createdAt = createdAt;
		}
		public ApplicationTable getApplicationTable() {
			return applicationTable;
		}
		public void setApplicationTable(ApplicationTable applicationTable) {
			this.applicationTable = applicationTable;
		}
		public int getJobSeekerId() {
			return jobSeekerId;
		}
		public void setJobSeekerId(int jobSeekerId) {
			this.jobSeekerId = jobSeekerId;
		}
		public Users getUsers() {
			return users;
		}
		public void setUsers(Users users) {
			this.users = users;
		}
}	
