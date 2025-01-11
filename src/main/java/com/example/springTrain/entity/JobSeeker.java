package com.example.springTrain.entity;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.springTrain.enums.Skills;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	private Long jobSeekerId;

	@Column(length = 100, nullable = false)
	private String fullName;
	
	@Column(length = 20)
	private String number;	
	
	@Column(length = 250)
	private List<Skills> skills;
	
	@Column(length = 1000)
	private String description;

	private String resumePath;
	
	private String profilePicturePath;
	
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

	public Long getJobSeekerId() {
		return jobSeekerId;
	}

	public void setJobSeekerId(Long jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Skills> getSkills() {
		return skills;
	}
	
	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
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

	public String getResumePath() {
		return resumePath;
	}

	public void setResumePath(String resumePath) {
		this.resumePath = resumePath;
	}
		
	
	
}	
