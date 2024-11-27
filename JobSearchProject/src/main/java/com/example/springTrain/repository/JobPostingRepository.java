package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer>{

	JobPosting findByJobId(Integer jobId);
	JobPosting findByEmployer_EmployerId(Integer employerId);
	JobPosting findByEmployer_EmployerIdAndJobId(Integer employerId,Integer jobId);

	//to find jobPosting by Order according to  CreatedAt field
	List<JobPosting> findAllByOrderByCreatedAtDesc(); 
	List<JobPosting> findAllByOrderByCreatedAtAsc(); 
	
	//finding in Jobposting employer companyName 
	List<JobPosting> findByEmployer_CompanyName(String companyName);
	List<JobPosting> findAllJobPostingsByEmployer_CompanyName(String companyName);
//	List<JobPosting> findByEmployer(Employer employer); 
//	List<JobPosting> findAllJobPostingByTitle(String title);
//	List<JobPosting> findAllJobPostingBySkills(String skills);
//	List<JobPosting> findAllJobPostingBySalaryRange(String salaryRange);
//	
	List<JobPosting> findAllJobPostingByJobType(JobType jobType);
	List<JobPosting> findAllJobPostingByExperienceLevel(ExperienceLevel expLevel);
	List<JobPosting> findAllJobPostingByCityLocation(CityLocation location);
	List<JobPosting> findAllJobPostingByJobCategory(JobCategory jobCategory);
	
	//to display jobPostings in pages
	Page<JobPosting> findAll(Pageable page);
	Page<JobPosting> findAllByOrderByCreatedAtDesc(Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobType(JobType jobType, Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobCategory(JobCategory jobCategory, Pageable pageable);
	Page<JobPosting> findAllJobPostingByCityLocation(CityLocation location, Pageable pageable);
	Page<JobPosting> findAllJobPostingByExperienceLevel(ExperienceLevel expLevel, Pageable pageable);
	Page<JobPosting> findAllJobPostingsByEmployer_CompanyName(String companyName, Pageable pageable);
	Page<JobPosting> findAllJobPostingByTitle(String title, Pageable pageable);
	Page<JobPosting> findAllJobPostingBySkills(String skills, Pageable pageable);
//	Page<JobPosting> findByTitleContainingOrSkillsContainingOrEmployer_CompanyNameContaining(String keyword, String keyword2,
//			String keyword3, Pageable pageable);
//    
	Integer countJobPostingByJobCategory(JobCategory jobCategory);
	

}
