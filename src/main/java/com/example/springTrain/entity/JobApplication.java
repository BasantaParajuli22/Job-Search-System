package com.example.springTrain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "job_application")
public class JobApplication{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationId;
	
	@ManyToOne
    @JoinColumn(name = "jobSeekerId", referencedColumnName="jobSeekerId", nullable = false)
	private JobSeeker jobSeeker;
	
	@ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName="jobId", nullable = false)	
	private JobPosting jobPosting;
	
    // Relationship with Employer (many applications for one employer)
    @ManyToOne
    @JoinColumn(name = "employerId", referencedColumnName = "employerId", nullable = false)
    private Employer employer;

	private String applicationStatus;//changeable by employer
	
	@CreationTimestamp
	private LocalDate appliedAt;
	
	@UpdateTimestamp
	private LocalDate updatedAt;

	
	//details submitted by jobseeker
	private String filePath;
	private String imagePath;
	
	@Column(length = 50)
	private String fullName;
	
	@Column(length = 20)
	private String email;
	
	@Column(length = 20)
	private String number;
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	public JobPosting getJobPosting() {
		return jobPosting;
	}

	public void setJobPosting(JobPosting jobPosting) {
		this.jobPosting = jobPosting;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public LocalDate getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(LocalDate appliedAt) {
		this.appliedAt = appliedAt;
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


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

}
