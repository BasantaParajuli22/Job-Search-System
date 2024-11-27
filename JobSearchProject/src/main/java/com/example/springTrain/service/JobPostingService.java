package com.example.springTrain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;
import com.example.springTrain.repository.JobPostingRepository;

@Service
public class JobPostingService {

    private JobPostingRepository jobPostingRepository;

	@Autowired
	public JobPostingService(JobPostingRepository jobPostingRepository) {
		this.jobPostingRepository = jobPostingRepository;
	}
	
	//find all jobpostings
    public List<JobPosting> findAllJobPostings() {
        return jobPostingRepository.findAll();
    }
    public List<JobPosting> findAllByOrderByCreatedAtDesc() {
        return jobPostingRepository.findAllByOrderByCreatedAtDesc();
    }
    
    //get values from JobType, ExperienceLevel, CityLocation enum values
    public JobType[] getAllJobTypes() {
        return JobType.values(); 
    }
    
    public ExperienceLevel[] getAllExperienceLevel() {
        return ExperienceLevel.values(); 
    }
    
    public CityLocation[] getAllCityLocation() {
        return CityLocation.values(); 
    }
    
	public JobCategory[] getAllCategories() {
		 return JobCategory.values(); 
	}
    public JobPosting getJobPostingById(Integer jobId) {
        return jobPostingRepository.findByJobId(jobId);
    }
        
    public JobPosting getJobPostingByEmployerId(Integer  employerId) {
    	return jobPostingRepository.findByEmployer_EmployerId(employerId);
    }
    
//    public List<JobPosting> findRelatedJobPostings(String category, int employerId) {
//        // Implement logic to find jobs related to the category or employer
//        return jobPostingRepository.findByCategoryOrEmployer(category, employerId);
//    }
    
    public JobPosting getJobPostingByEmployerIdAndJobId(Integer employerId, Integer jobId) {
    	return jobPostingRepository.findByEmployer_EmployerIdAndJobId(employerId,jobId);
    }

    public JobPosting createJobPosting(JobPosting jobPosting, Employer employer) {
    	// Set the employer for the job posting
	    jobPosting.setEmployer(employer);
        return jobPostingRepository.save(jobPosting);
    }

    public JobPosting updateJobPosting(Integer jobId, JobPosting jobPosting) {
        jobPosting.setJobId(jobId);
        return jobPostingRepository.save(jobPosting);
    }
    
    public void deleteJobPostingById(Integer id) {
        jobPostingRepository.deleteById(id);
    }
    
    //delete jobposting 
    public void deleteJobPosting(JobPosting jobPosting) {
        jobPostingRepository.delete(jobPosting);
    }
    
    //finding specific list of JobPosting of companyName
	public List<JobPosting> findByEmployerCompanyName(String companyName) {
        return jobPostingRepository.findByEmployer_CompanyName(companyName);
	}

	
//	//methods 
//	//to find all jobPostings according to search category
//	public List<JobPosting> findAllJobPostingByCategoryId(Integer categoryId) {
//    	return jobPostingRepository.findAllJobPostingByJobCategory_CategoryId(categoryId);
//	}
//	
//	public List<JobPosting> findAllJobPostingByCategoryName(String categoryName) {
//		return jobPostingRepository.findAllJobPostingByJobCategory_CategoryName(categoryName);
//	}
//
//	public List<JobPosting> findAllJobPostingByJobTitle(String title) {
//    	return jobPostingRepository.findAllJobPostingByTitle(title);
//	}
//	
//	public List<JobPosting> findAllJobPostingBySkills(String skills) {
//    	return jobPostingRepository.findAllJobPostingBySkills(skills);
//	}
//	
//	public List<JobPosting> findAllJobPostingBySalary(String salaryRange) {
//    	return jobPostingRepository.findAllJobPostingBySalaryRange(salaryRange);
//	}

	
	//finding JobPosting By enum values 
	public List<JobPosting> findAllJobPostingByCityLocation(CityLocation location) {
		return jobPostingRepository.findAllJobPostingByCityLocation(location);
	}
	
	public List<JobPosting> findAllJobPostingByJobType(JobType jobType) {
    	return jobPostingRepository.findAllJobPostingByJobType(jobType);
	}
	
	public List<JobPosting> findAllJobPostingByExperienceLevel(ExperienceLevel expLevel) {
    	return jobPostingRepository.findAllJobPostingByExperienceLevel(expLevel);
	}

	public List<JobPosting> findAllJobPostingByJobCategory(JobCategory jobCategory) {
		return jobPostingRepository.findAllJobPostingByJobCategory(jobCategory);
	}
	

    //method to calculate remaining time to apply for job
	public String getRemainingTime(LocalDate applicationDeadline) {
		 
		//if deadline is before current time 
		//applying time has passed already
		if (applicationDeadline.isBefore(LocalDate.now())) {
	        return "Application deadline has passed";
	    }
		
		//return days between applicationDeadline and current Date
		long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), applicationDeadline);
	    return daysLeft + " days ";		
	}

	
	//to get page and size
	public Page<JobPosting> getPaginatedJobPostingInDesc(int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		System.out.println("Page Requested: " + page);
		System.out.println("Page Size: " + size);

		return jobPostingRepository.findAllByOrderByCreatedAtDesc(pageable);
	}
	
//	public Page<JobPosting> getPaginatedJobPostingByJobCategory(JobCategory jobCategory,int page,int size) {
//		Pageable pageable = PageRequest.of(page, size);		
//		return jobPostingRepository.findAllJobPostingByJobCategory(jobCategory,pageable);
//	}
//	
//	public Page<JobPosting> getPaginatedJobPostingByJobType(JobType jobType,int page,int size) {
//		Pageable pageable = PageRequest.of(page, size);		
//		return jobPostingRepository.findAllJobPostingByJobType(jobType,pageable);	
//	}
//	
//	public Page<JobPosting> getPaginatedJobPostingByCityLocation(CityLocation location,int page,int size) {
//		Pageable pageable = PageRequest.of(page, size);		
//		return jobPostingRepository.findAllJobPostingByCityLocation(location,pageable);	
//	}
//	
//	public Page<JobPosting> getPaginatedJobPostingByExpLevel(ExperienceLevel expLevel, int page, int size) {
//		Pageable pageable = PageRequest.of(page, size);		
//		return jobPostingRepository.findAllJobPostingByExperienceLevel(expLevel,pageable);	
//	}
	

//	public Page<JobPosting> findAllJobPostingByKeyword(String keyword,int page,int size) {
//		Pageable pageable = PageRequest.of(page, size);
//    	return jobPostingRepository.findByTitleContainingOrSkillsContainingOrEmployer_CompanyNameContaining(keyword, keyword, keyword, pageable);
//	}
//	
	
//	public Integer countJobPostingByJobCategory(JobCategory jobCategory) {
//		return jobPostingRepository.countJobPostingByJobCategory(jobCategory);
//
//	}


	
}
