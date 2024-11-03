package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.dto.CityLocation;
import com.example.springTrain.dto.ExperienceLevel;
import com.example.springTrain.dto.JobType;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer>{

	JobPosting findByJobId(Integer jobId);
	JobPosting findByEmployer_EmployerId(Integer employerId);
	JobPosting findByEmployer_EmployerIdAndJobId(Integer employerId,Integer jobId);

	//finding in Jobposting employer companyName 
	List<JobPosting> findByEmployerCompanyName(String companyName);

	List<JobPosting> findByEmployer(Employer employer); // Use the employer object directly

	List<JobPosting> findAllJobPostingsByEmployer_CompanyName(String companyName);
	List<JobPosting> findAllJobPostingByJobCategory_CategoryName(String categoryName);
	List<JobPosting> findAllJobPostingByJobCategory_CategoryId(Integer categoryId);
	List<JobPosting> findAllJobPostingByTitle(String title);
	List<JobPosting> findAllJobPostingBySalaryRange(String salaryRange);
	
	List<JobPosting> findAllJobPostingByJobType(JobType jobType);
	List<JobPosting> findAllJobPostingByExperienceLevel(ExperienceLevel expLevel);
	List<JobPosting> findAllJobPostingByCityLocation(CityLocation location);


    
}
