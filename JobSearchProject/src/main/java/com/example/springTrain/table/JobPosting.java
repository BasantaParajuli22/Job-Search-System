package com.example.springTrain.table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.springTrain.dto.CityLocation;
import com.example.springTrain.dto.ExperienceLevel;
import com.example.springTrain.dto.JobType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private String requirements;
	private String jobDescription;
	private String salaryRange;
	private LocalDate applicationDeadline;
	private String contactEmail;
	private String skills;
	private boolean remote;
	
	@Enumerated(EnumType.STRING)
	private CityLocation cityLocation;

	@Enumerated(EnumType.STRING)
	private JobType jobType;  // Full-Time, Part-Time, Contract
	
	@Enumerated(EnumType.STRING)
	private ExperienceLevel experienceLevel;  // Entry, Mid, Senior
	
	@CreationTimestamp
	private LocalDate createdAt;
	
	@UpdateTimestamp
	private LocalDate updatedAt;

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

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}

	public List<JobApplication> getJobApplication() {
		return JobApplication;
	}

	public void setJobApplication(List<JobApplication> jobApplication) {
		JobApplication = jobApplication;
	}

	public List<SavedJobs> getSavedJobs() {
		return savedJobs;
	}

	public void setSavedJobs(List<SavedJobs> savedJobs) {
		this.savedJobs = savedJobs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSalaryRange() {
		return salaryRange;
	}

	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}

	public LocalDate getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(LocalDate applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public ExperienceLevel getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(ExperienceLevel experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}


	public CityLocation getCityLocation() {
		return cityLocation;
	}

	public void setCityLocation(CityLocation cityLocation) {
		this.cityLocation = cityLocation;
	}
	

}
	
