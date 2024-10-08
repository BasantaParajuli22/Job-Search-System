package com.example.springTrain.user;


import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;
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
		private Integer job_seeker_id;
		
		@OneToOne
		@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
		private User user;
		
		private String email;
		private String number;
		private String address;
		private String skills;//skills will be in list//
//		private String resume;

		@CreationTimestamp
		private Instant createdAt;
		
		//getters and setters
		public int getJob_seeker_id() {
			return job_seeker_id;
		}
		public void setJobSeeker_id(int job_seeker_id) {
			this.job_seeker_id = job_seeker_id;
		}
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
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
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
}	
