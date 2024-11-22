package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Admin;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.AdminRepository;

@Service
public class AdminService {
	
	private AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
		
	public Admin findByUsers(Users user) {
		return adminRepository.findByUsers(user);
	}

	public Admin findByUsername(String loggedinusername) {
		return adminRepository.findByUsername(loggedinusername);
	}
}

