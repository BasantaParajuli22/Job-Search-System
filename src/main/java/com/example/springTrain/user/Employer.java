package com.example.springTrain.user;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employers")
public class Employer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employerId;
	
	@OneToOne
    @JoinColumn(name = "user_id", referencedColumnName="user_id", nullable = false)
	private User user;
	
	private String companyName;
	private String companyDescription;
	private String contactNumber;
	private String address;
	private String email;
	private String website;
	
	@CreationTimestamp
	private Instant createdAt;
	
	
	//getters and setters
	public int getEmployerId() {
		return employerId;
	}
	public void setEmployerId(int employerId) {
		this.employerId = employerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyDescription() {
		return companyDescription;
	}
	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
 