package com.example.springTrain.dto;

import com.example.springTrain.enums.Usertype;

public class EmployerDTO {
	
	private Long userId;
	private String companyName;
	private String companyDescription;
    private String password;//for getting data from form not to SAVE//
    private String confirmPassword;
	private String address;
	private String number;
	private String email;
    private Usertype usertype;

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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
