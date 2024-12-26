package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Integer>{

	JobApplication findByApplicationId(Integer applicationId);
	//JobApplication findByJobSeeker_JobSeekerIdAndJobPosting_JobId(Integer jobSeekerId,Integer jobId);
	JobApplication findByJobPosting_JobIdAndJobSeeker_JobSeekerId(Integer jobId, Integer jobSeekerId);

	List<JobApplication> findByEmployer_employerId(Integer employerId);
	List<JobApplication> findByJobSeeker_JobSeekerId(Integer jobSeekerId);
	
	
	Integer countJobApplicationByJobPosting_JobId(Integer jobId);
	List<JobApplication> findByEmployer_EmployerIdAndJobPosting_JobId(Integer employerId, Integer jobId);

}

