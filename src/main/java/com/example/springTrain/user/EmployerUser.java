package com.example.springTrain.user;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.example.springTrain.home.Usertype;

//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employer_user")
public class EmployerUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//@Column(name = "true")
	private String username;
	
	private String email;
	private String number;
	private String password;
	
	@CreationTimestamp
	private Instant createdAt;
	
	private String sesion;//temporary password
	
	@Enumerated(value = EnumType.STRING)
	private Usertype Usertype;

	public String getId() {
		return username;
	}
	public void serId(int id) {
		 this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSesion() {
		return sesion;
	}
	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	public Usertype getUserType() {
		return Usertype;
	}
	public void setUserType(Usertype Usertype) {
		this.Usertype = Usertype;
	}
}
 