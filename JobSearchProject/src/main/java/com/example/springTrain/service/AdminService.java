package com.example.springTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.user.Admin;
import com.example.springTrain.user.AdminRepository;
import com.example.springTrain.user.User;

@Service
public class AdminService {
	
	private AdminRepository adminRepository;

	@Autowired
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
		
	public Admin findByUser(User user) {
		return adminRepository.findByUser(user);
	}
}

