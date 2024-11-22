package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.entity.Admin;
import com.example.springTrain.entity.Users;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

	Admin findByUsers(Users user);

	Admin findByUsername(String loggedinusername);

}
