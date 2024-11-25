package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Admin;
import com.example.springTrain.entity.Users;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer>{

	Admin findByUsers(Users user);

	Admin findByUsername(String loggedinusername);

}
