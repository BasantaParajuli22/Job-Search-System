package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.table.JobPosting;
import com.example.springTrain.user.Employer;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer>{

	JobPosting findByJobId(Integer jobId);
	JobPosting findByEmployer_EmployerId(Integer employerId);
	JobPosting findByEmployer_EmployerIdAndJobId(Integer employerId,Integer jobId);

	//finding in Jobposting employer companyName 
	List<JobPosting> findByEmployerCompanyName(String companyName);

	List<JobPosting> findByEmployer(Employer employer); // Use the employer object directly
	//List<JobPosting> findByCategoryOrEmployer(String category, Integer employerId);

    
}
