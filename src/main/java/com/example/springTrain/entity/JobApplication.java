package com.example.springTrain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

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
	private Integer applicationId;
	
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
	private String fileName;
	
	@CreationTimestamp
	private LocalDate appliedAt;

	
	
	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
