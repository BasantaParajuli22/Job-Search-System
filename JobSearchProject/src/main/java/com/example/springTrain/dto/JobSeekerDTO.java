package com.example.springTrain.dto;

import com.example.springTrain.enums.Usertype;

public class JobSeekerDTO {
	
	private Integer jobSeekerId;
	private String jobSeekerUsername;
    private String password;
    private String confirmPassword;
    private String email;
    private String number;
    private String address;
    private String skills;
    private Usertype usertype;

//    private String resume;

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public Usertype getUsertype() {
		return usertype;
	}
	public void setUsertype(Usertype usertype) {
		this.usertype = usertype;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getJobSeekerUsername() {
		return jobSeekerUsername;
	}
	public void setJobSeekerUsername(String jobSeekerUsername) {
		this.jobSeekerUsername = jobSeekerUsername;
	}
	public Integer getJobSeekerId() {
		return jobSeekerId;
	}
	public void setJobSeekerId(Integer jobSeekerId) {
		this.jobSeekerId = jobSeekerId;
	}


//	public String getResume() {
//		return resume;
//	}
//	public void setResume(String resume) {
//		this.resume = resume;
//	}
}
