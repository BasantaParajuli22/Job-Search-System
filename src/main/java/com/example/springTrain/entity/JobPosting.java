package com.example.springTrain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	private Long jobId;
	
	@ManyToOne
    @JoinColumn(name = "employerId", referencedColumnName = "employerId", nullable = false)
	private Employer employer;
	
	//JobApplication should have foreign key of jobPosting 
	@OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    private List<JobApplication> jobApplication = new ArrayList<>();

	@OneToMany(mappedBy ="jobPosting", cascade = CascadeType.ALL)
	private List<SavedJobs> savedJobs;

	@Column(length = 50)
	private String title;
	
	@Column(length = 1000)
	private String requirements;
	
	@Column(length = 1000)
	private String jobDescription;
	
	@Column(length = 250)
	private String salaryRange;	
	
	private boolean remote;
	
	@Enumerated(EnumType.STRING)
	private CityLocation cityLocation;

	@Enumerated(EnumType.STRING)
	private JobType jobType;  // Full-Time, Part-Time, Contract
	
	@Enumerated(EnumType.STRING)
	private ExperienceLevel experienceLevel;  // Entry, Mid, Senior
	
	@Enumerated(EnumType.STRING)
	private JobCategory jobCategory; 
	
	@CreationTimestamp
	private LocalDate createdAt;
	
	@UpdateTimestamp
	private LocalDate updatedAt;

	private LocalDate applicationDeadline;
	private boolean available;
	
	//auto generating hasCode and equals method
	//to check if there is unique jobPosting or not
	//based on hash code
	//comparing jobPost object with another jobPost object
	@Override
	public int hashCode() {
		return Objects.hash(jobId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobPosting other = (JobPosting) obj;
		return Objects.equals(jobId, other.jobId);
	}

	
	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public List<JobApplication> getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(List<JobApplication> jobApplication) {
		this.jobApplication = jobApplication;
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

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
	
