package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.entity.JobCategory;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer>{

	String findByCategoryId(Integer categoryId);
	String findByCategoryName(String categoryName);
	
}
