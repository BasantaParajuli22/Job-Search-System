package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.table.Admin;
import com.example.springTrain.table.Users;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

	Admin findByUsers(Users user);

	Admin findByUsername(String loggedinusername);

}
