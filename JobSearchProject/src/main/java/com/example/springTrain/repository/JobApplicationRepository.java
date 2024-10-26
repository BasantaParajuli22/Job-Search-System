package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.table.JobApplication;
import com.example.springTrain.user.Employer;
import com.example.springTrain.user.JobSeeker;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Integer>{

	JobApplication findByApplicationId(Integer applicationId);
	JobApplication findByJobSeeker_JobSeekerIdAndJobPosting_JobId(Integer jobSeekerId,Integer jobId);

	//find all jobApplication by JobSeekerUsername and CompanyName
	List<JobApplication> findAllJobApplicationByJobSeeker_JobSeekerUsername(String jobSeekerUsername);
	List<JobApplication> findAllJobApplicationByEmployer_CompanyName(String companyName);


}
