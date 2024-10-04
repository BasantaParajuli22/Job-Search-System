package com.example.table;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "company_reviews")
public class Company_reviews{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int review_id;
	
	//foreign keys
	private int employer_id;
	private int job_seeker_id;

	private int rating;
	private String comment;
	
	@CreationTimestamp
	private Instant reviewDate;
	
	//getters and setters
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public int getEmployer_id() {
		return employer_id;
	}
	public void setEmployer_id(int employer_id) {
		this.employer_id = employer_id;
	}
	public int getJob_seeker_id() {
		return job_seeker_id;
	}
	public void setJob_seeker_id(int job_seeker_id) {
		this.job_seeker_id = job_seeker_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
