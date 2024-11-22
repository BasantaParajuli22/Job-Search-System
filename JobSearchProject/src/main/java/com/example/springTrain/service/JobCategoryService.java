package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.JobCategory;
import com.example.springTrain.repository.JobCategoryRepository;

@Service
public class JobCategoryService {

	public JobCategoryRepository jobCategoryRepository;
	
	@Autowired
	public JobCategoryService(JobCategoryRepository jobCategoryRepository){
		this.jobCategoryRepository = jobCategoryRepository;
	}

	public List<JobCategory> getAllCategories() {
		return jobCategoryRepository.findAll();
	}

}
