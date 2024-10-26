package com.example.springTrain.table;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "job_category")
public class JobCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	private String categoryName;

	@OneToMany(mappedBy ="jobCategory", cascade = CascadeType.ALL)
    private List<JobPosting> jobPosting;
	
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<JobPosting> getJobPosting() {
		return jobPosting;
	}
	public void setJobPostings(List<JobPosting> jobPosting) {
		this.jobPosting = jobPosting;
	}
}
