package com.example.springTrain.table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_posting")
public class JobPosting {
	
	//When you create a new JobPosting, the jobId will now be null instead of 0.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobId;
	
	@ManyToOne
    @JoinColumn(name = "employerId", referencedColumnName = "employerId", nullable = false)
	private Employer employer;
	
	@ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId", nullable = false)
	private JobCategory jobCategory;

	//JobApplication should have foreign key of jobPosting 
	@OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> JobApplication = new ArrayList<>();

	@OneToMany(mappedBy ="jobPosting", cascade = CascadeType.ALL)
	private List<SavedJobs> savedJobs;

	private String title;
	private String location;
	private String requirements;
	private String jobDescription;
	private String jobType;  // Full-Time, Part-Time, Contract
	private String salaryRange;
	private String status;  // active, closed, on hold
	private LocalDate applicationDeadline;
	
	private String skills;
	
	private String experienceLevel;  // Entry, Mid, Senior
	private boolean remote;
	
	private LocalDate startDate;
	private LocalDate endDate;
	private String contactEmail;
	
	@CreationTimestamp
	private String createdAt;
	@UpdateTimestamp
	private String updatedAt;
	
	
	
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public List<JobApplication> getJobApplication() {
		return JobApplication;
	}
	public void setJobApplication(List<JobApplication> jobApplication) {
		JobApplication = jobApplication;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getSalaryRange() {
		return salaryRange;
	}
	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getApplicationDeadline() {
		return applicationDeadline;
	}
	public void setApplicationDeadline(LocalDate applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}
	public String getExperienceLevel() {
		return experienceLevel;
	}
	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public JobCategory getJobCategory() {
		return jobCategory;
	}
	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}
	public boolean isRemote() {
		return remote;
	}
	public void setRemote(boolean remote) {
		this.remote = remote;
	}
	public List<SavedJobs> getSavedJobs() {
		return savedJobs;
	}
	public void setSavedJobs(List<SavedJobs> savedJobs) {
		this.savedJobs = savedJobs;
	}

}
	
