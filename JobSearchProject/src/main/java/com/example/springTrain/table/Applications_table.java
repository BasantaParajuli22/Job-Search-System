package com.example.springTrain.table;


import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "applications_table")
public class Applications_table {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int application_id;
	
	//foreign key
	private int job_seeker_id;
	private String job_id;
	
	private String application_status;
	private String job_type;
	private String cover_letter;
	
	@CreationTimestamp
	private Instant appliedAt;
	
	//getters and setters
	public int getJob_seeker_id() {
		return job_seeker_id;
	}
	public void setJob_seeker_id(int job_seeker_id) {
		this.job_seeker_id = job_seeker_id;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getApplication_status() {
		return application_status;
	}
	public void setApplication_status(String application_status) {
		this.application_status = application_status;
	}
	public String getJob_type() {
		return job_type;
	}
	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}
	public String getCover_letter() {
		return cover_letter;
	}
	public void setCover_letter(String cover_letter) {
		this.cover_letter = cover_letter;
	}

	
}
