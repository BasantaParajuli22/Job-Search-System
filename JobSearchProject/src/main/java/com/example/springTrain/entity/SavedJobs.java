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
@Table(name= "saved_jobs")
public class SavedJobs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer savedId;
	
	@ManyToOne
	@JoinColumn(name = "jobSeekerId", referencedColumnName ="jobSeekerId",nullable = false)
	private JobSeeker jobSeeker;
	
	@ManyToOne
	@JoinColumn(name = "jobId", referencedColumnName ="jobId", nullable =false)
	private JobPosting jobPosting;
	
	@CreationTimestamp
	private LocalDate savedAt;

	public Integer getSavedId() {
		return savedId;
	}

	public void setSavedId(Integer savedId) {
		this.savedId = savedId;
	}


	public LocalDate getSavedAt() {
		return savedAt;
	}

	public void setSavedAt(LocalDate savedAt) {
		this.savedAt = savedAt;
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

}
