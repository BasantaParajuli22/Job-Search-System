package com.example.springTrain.table;


import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.example.springTrain.user.Employer;
import com.example.springTrain.user.JobSeeker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "application_table")
public class ApplicationTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer applicationId;
	
	@OneToOne
    @JoinColumn(name = "jobSeekerId", referencedColumnName="jobSeekerId", nullable = false)
	private JobSeeker jobSeeker;
	
	@ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName="jobId", nullable = false)	
	private JobPosting jobPosting;
	
    // Relationship with Employer (many applications for one employer)
    @ManyToOne
    @JoinColumn(name = "employerId", referencedColumnName = "employerId", nullable = false)
    private Employer employer;

	
	private String applicationStatus;
	private String jobType;
	private String coverLetter;
	
	@CreationTimestamp
	private Instant appliedAt;
	
	//getters and setters
	public JobPosting getJobPosting() {
		return jobPosting;
	}
	public void setJobPosting(JobPosting jobPosting) {
		this.jobPosting = jobPosting;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getCoverLetter() {
		return coverLetter;
	}
	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	
}
