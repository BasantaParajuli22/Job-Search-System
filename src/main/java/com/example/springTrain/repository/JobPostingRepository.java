package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Integer>{

	JobPosting findByJobId(Integer jobId);
	JobPosting findByEmployer_EmployerIdAndJobId(Integer employerId,Integer jobId);
	JobPosting findByJobIdAndJobApplication_JobSeeker_JobSeekerId(Integer jobId, Integer jobSeekerId);

	//to find jobPosting by Order according to  CreatedAt field
	List<JobPosting> findAllByOrderByCreatedAtDesc(); 
	List<JobPosting> findAllByOrderByCreatedAtAsc(); 
	
	//finding in Jobposting employer companyName 
	List<JobPosting> findByEmployer(Employer employer); 
	List<JobPosting> findByEmployer_EmployerId(Integer employerId);
	List<JobPosting> findByEmployer_CompanyName(String companyName);
	
	List<JobPosting> findAllJobPostingByTitle(String title);
	List<JobPosting> findAllJobPostingBySalaryRange(String salaryRange);
	
	//to display jobPostings in List
	List<JobPosting> findAllJobPostingByJobType(JobType jobType);
	List<JobPosting> findAllJobPostingByExperienceLevel(ExperienceLevel expLevel);
	List<JobPosting> findAllJobPostingByCityLocation(CityLocation location);
	List<JobPosting> findAllJobPostingByJobCategory(JobCategory jobCategory);
	
	//to display jobPostings in pages
	Page<JobPosting> findAll(Pageable page);
	Page<JobPosting> findAllByOrderByCreatedAtDesc(Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobCategory(JobCategory jobCategory, Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobType(JobType jobType, Pageable pageable);
	Page<JobPosting> findAllJobPostingByCityLocation(CityLocation location, Pageable pageable);
	Page<JobPosting> findAllJobPostingByExperienceLevel(ExperienceLevel expLevel, Pageable pageable);
	Page<JobPosting> findByTitleContainingOrSalaryRangeContainingOrEmployer_CompanyNameContaining(String k1, String k2,String k3, Pageable pageable);
    
	
	Page<JobPosting> findByAvailable(Boolean available, Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobCategoryAndAvailable(JobCategory jobCategory, boolean b, Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobTypeAndAvailable(JobType jobType, boolean b, Pageable pageable);
	Page<JobPosting> findAllJobPostingByCityLocationAndAvailable(CityLocation location, boolean b, Pageable pageable);
	Page<JobPosting> findAllJobPostingByExperienceLevelAndAvailable(ExperienceLevel expLevel, boolean b,
			Pageable pageable);
	Page<JobPosting> findByTitleContainingOrSalaryRangeContainingOrEmployer_CompanyNameContainingAndAvailable(
			String keyword, String keyword2, String keyword3, boolean b, Pageable pageable);
	
	
	//for counting 
	List<Integer> countByJobCategory(JobCategory jobCategory);
	Integer countJobPostingByJobCategory(JobCategory jobCategory);
	Integer countJobPostingByJobType(JobType type);
	Integer countJobPostingByExperienceLevel(ExperienceLevel exp);
	Integer countJobPostingByCityLocation(CityLocation city);


}
