package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{

	JobApplication findByApplicationId(Long applicationId);
	//JobApplication findByJobSeeker_JobSeekerIdAndJobPosting_JobId(Long jobSeekerId,Long jobId);
	JobApplication findByJobPosting_JobIdAndJobSeeker_JobSeekerId(Long jobId, Long jobSeekerId);

	List<JobApplication> findByEmployer_employerId(Long employerId);
	List<JobApplication> findByJobSeeker_JobSeekerId(Long jobSeekerId);
	
	
	Long countJobApplicationByJobPosting_JobId(Long jobId);
	List<JobApplication> findByEmployer_EmployerIdAndJobPosting_JobId(Long employerId, Long jobId);
		
	List<JobApplication> findByEmployer_EmployerIdAndJobPosting_JobIdAndApplicationStatus(Long employerId, Long jobId,
			String applicationStatus);
	
	
	Page<JobApplication> findByEmployer_EmployerIdAndJobPosting_JobId(Long employerId, Long jobId, Pageable pageable);
	Page<JobApplication> findByEmployer_EmployerIdAndJobPosting_JobIdAndApplicationStatus(Long employerId, Long jobId,
			String applicationStatus, Pageable pageable);

}

