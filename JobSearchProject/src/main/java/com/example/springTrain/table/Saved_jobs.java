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
public class Saved_jobs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int saved_id;
	
	//foreign key
	private int job_id;
	private int job_seeker_id;
	
	@CreationTimestamp
	private Instant savedAt;

	public int getSaved_id() {
		return saved_id;
	}

	public void setSaved_id(int saved_id) {
		this.saved_id = saved_id;
	}

	public int getJob_id() {
		return job_id;
	}

	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}

	public int getJob_seeker_id() {
		return job_seeker_id;
	}

	public void setJob_seeker_id(int job_seeker_id) {
		this.job_seeker_id = job_seeker_id;
	}
}
