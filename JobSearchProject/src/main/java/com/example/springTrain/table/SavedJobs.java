package com.example.springTrain.table;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "saved_jobs")
public class SavedJobs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer savedId;
	
	//foreign key
	private int jobId;
	private int job_seekerId;
	
	@CreationTimestamp
	private Instant savedAt;

	public Integer getSavedId() {
		return savedId;
	}

	public void setSavedId(Integer savedId) {
		this.savedId = savedId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getJob_seekerId() {
		return job_seekerId;
	}

	public void setJob_seekerId(int job_seekerId) {
		this.job_seekerId = job_seekerId;
	}

	public Instant getSavedAt() {
		return savedAt;
	}

	public void setSavedAt(Instant savedAt) {
		this.savedAt = savedAt;
	}


}
