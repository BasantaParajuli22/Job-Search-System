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
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{

	JobPosting findByJobId(Long jobId);
	JobPosting findByEmployer_EmployerIdAndJobId(Long employerId,Long jobId);
	JobPosting findByJobIdAndJobApplication_JobSeeker_JobSeekerId(Long jobId, Long jobSeekerId);

	//to find jobPosting by Order according to  CreatedAt field
	List<JobPosting> findAllByOrderByCreatedAtDesc(); 
	List<JobPosting> findAllByOrderByCreatedAtAsc(); 
	
	//finding in Jobposting employer companyName 
	List<JobPosting> findByEmployer(Employer employer); 
	List<JobPosting> findByEmployer_EmployerId(Long employerId);
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
    
	Page<JobPosting> findByAvailable(Boolean available, Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobCategoryAndAvailable(JobCategory jobCategory, boolean b, Pageable pageable);
	Page<JobPosting> findAllJobPostingByJobTypeAndAvailable(JobType jobType, boolean b, Pageable pageable);
	Page<JobPosting> findAllJobPostingByCityLocationAndAvailable(CityLocation location, boolean b, Pageable pageable);
	Page<JobPosting> findAllJobPostingByExperienceLevelAndAvailable(ExperienceLevel expLevel, boolean b,
			Pageable pageable);
	
	Page<JobPosting> findByTitleContainingIgnoreCaseOrEmployer_CompanyNameContainingIgnoreCaseAndAvailable(
			String keyword, String keyword2,boolean b, Pageable pageable);
	
	//for counting 
	Long countByJobCategory(JobCategory jobCategory);
	Long countJobPostingByJobCategory(JobCategory jobCategory);
	Long countJobPostingByJobType(JobType type);
	Long countJobPostingByExperienceLevel(ExperienceLevel exp);
	Long countJobPostingByCityLocation(CityLocation city);
	
	Long countJobPostingByJobCategoryAndAvailable(JobCategory jobCategory, boolean b);
	Long countJobPostingByJobTypeAndAvailable(JobType ty, boolean b);
	Long countJobPostingByExperienceLevelAndAvailable(ExperienceLevel ty, boolean b);
	Long countJobPostingByCityLocationAndAvailable(CityLocation ty, boolean b);
	


}
