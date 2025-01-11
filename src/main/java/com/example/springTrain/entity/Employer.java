package com.example.springTrain.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employers")
public class Employer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employerId;
	
	@Column(length = 100, nullable = false)
	private String companyName;
	
	@Column(length = 250)
	private String address;
	
	@Column(length = 1000)
	private String companyDescription;
	
	@Column(length = 20)
	private String number;

	@Column(length = 100)
	private String website;

	private String companyLogoPath;
	
	@CreationTimestamp
	private LocalDate createdAt;
  
	@OneToOne
    @JoinColumn(name = "userId", referencedColumnName="userId", nullable = false)
	private Users users;
	
	// An employer can have multiple jobs //its reference only
	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
	private List<JobPosting> jobPosting;

	public Long getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Long employerId) {
		this.employerId = employerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public List<JobPosting> getJobPosting() {
		return jobPosting;
	}

	public void setJobPosting(List<JobPosting> jobPosting) {
		this.jobPosting = jobPosting;
	}

	public String getCompanyLogoPath() {
		return companyLogoPath;
	}

	public void setCompanyLogoPath(String companyLogoPath) {
		this.companyLogoPath = companyLogoPath;
	}
	
	
	
	
}
 