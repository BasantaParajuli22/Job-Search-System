package com.example.springTrain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer>{

	Admin findByUser(User user);

}
