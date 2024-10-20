package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.user.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Users findByUsername(String username);
	Users findByEmail(String email);
	Users findByEmailAndPassword(String email,String password);
}