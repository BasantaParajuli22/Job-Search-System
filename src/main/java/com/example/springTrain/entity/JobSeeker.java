package com.example.springTrain.entity;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobSeekers")
public class JobSeeker {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobSeekerId;

	private String fullName;
	private String number;	
	private String skills;
//	private String resume;

	@CreationTimestamp
	private LocalDate createdAt;

	//The owning side manages the relationship, defined with @JoinColumn.
	//The inverse side is the side that simply mirrors the relationship using mappedBy.
	//mappedBy is  used on the inverse side to specify the field name
	// in the owning entity that "owns" the relationship.	
	//one jobSeeker can apply to many jobApplication
	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
	private Users users;
	
	@OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
	private List<JobApplication> jobApplication;
		
	@OneToMany(mappedBy ="jobSeeker", cascade = CascadeType.ALL)
	private List<SavedJobs> savedJobs;
		
	
	public Integer getJobSeekerId() {
		return jobSeekerId;
	}

	public void setJobSeekerId(Integer jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public List<JobApplication> getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(List<JobApplication> jobApplication) {
		this.jobApplication = jobApplication;
	}

	public List<SavedJobs> getSavedJobs() {
		return savedJobs;
	}

	public void setSavedJobs(List<SavedJobs> savedJobs) {
		this.savedJobs = savedJobs;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}	
