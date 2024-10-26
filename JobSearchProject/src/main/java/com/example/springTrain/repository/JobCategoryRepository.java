package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.table.JobCategory;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer>{
	
}
