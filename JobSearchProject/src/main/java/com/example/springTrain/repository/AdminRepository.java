package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.user.Admin;
import com.example.springTrain.user.Users;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

	Admin findByUsers(Users user);

}
