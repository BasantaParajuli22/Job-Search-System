package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Integer>{

	JobApplication findByApplicationId(Integer applicationId);
	JobApplication findByJobSeeker_JobSeekerIdAndJobPosting_JobId(Integer jobSeekerId,Integer jobId);
	JobApplication findByJobPosting_JobIdAndJobSeeker_JobSeekerId(Integer jobId, Integer jobSeekerId);

	//find all jobApplication by JobSeekerUsername and CompanyName
//	List<JobApplication> findByJobSeeker_JobSeekerUsername(String jobSeekerUsername);
	List<JobApplication> findByEmployer_CompanyName(String companyName);
	List<JobApplication> findByEmployer_employerId(Integer employerId);
	List<JobApplication> findByEmployer_CompanyNameAndJobPosting_JobId(String companyName, Integer jobId);
	
	
	Integer countJobApplicationByJobPosting_JobId(Integer jobId);
	List<JobApplication> findByJobSeeker_JobSeekerId(Integer jobSeekerId);

}

