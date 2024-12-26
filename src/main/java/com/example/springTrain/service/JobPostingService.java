package com.example.springTrain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
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
	
	public long countAllJobPosting() {
		return jobPostingRepository.count();
	}

	public JobPosting getByJobId(Integer jobId) {
		return jobPostingRepository.findByJobId(jobId);
	}
	
//	public List<JobPosting> getAllAvailablePosts() {
//		return jobPostingRepository.findByAvailable(true);
//	}
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
	public JobPosting getJobPostingByIdAndJobSekerId(Integer jobId, Integer jobSeekerId) {
		return jobPostingRepository.findByJobIdAndJobApplication_JobSeeker_JobSeekerId(jobId,jobSeekerId);
	}    
	public List<JobPosting> findByEmployerCompanyName(String companyName) {
		return jobPostingRepository.findByEmployer_CompanyName(companyName);

	}
    public List<JobPosting> getJobPostingByEmployerId(Integer  employerId) {
    	return jobPostingRepository.findByEmployer_EmployerId(employerId);
    }
    
    public JobPosting getJobPostingByEmployerIdAndJobId(Integer employerId, Integer jobId) {
    	return jobPostingRepository.findByEmployer_EmployerIdAndJobId(employerId,jobId);
    }

    public JobPosting createJobPosting(JobPosting jobPosting, Employer employer) {
    	// Set the employer for the job posting
	    jobPosting.setEmployer(employer);
	    
	    if(jobPosting.getApplicationDeadline() != null ) {
		    jobPosting.setAvailable(true);
	    }else {
	    	jobPosting.setAvailable(false);
	    }
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

	//save available to false
	public void saveJobPostingAvailability(JobPosting jobPost) {
		jobPost.setAvailable(false);
		jobPostingRepository.save(jobPost);
	}

    // to calculate remaining time to apply for job
	public String getRemainingTime(LocalDate applicationDeadline) {
		 
		//if deadline is before current time 
		//applying time has passed already
		if ( applicationDeadline == null || applicationDeadline.isBefore(LocalDate.now())) {
	        return "Deadline has passed";
	    }
		//return days between applicationDeadline and current Date
		long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), applicationDeadline);
	    return daysLeft + " days ";		
	}
	
    //finding specific list of JobPosting of companyName
	public List<JobPosting> findByEmployerId(Integer companyName) {
        return jobPostingRepository.findByEmployer_EmployerId(companyName);
	}
	
	//finding JobPosting in list
	public List<JobPosting> findAllJobPostingByJobTitle(String title) {
    	return jobPostingRepository.findAllJobPostingByTitle(title);
	}
	
	public List<JobPosting> findAllJobPostingBySalary(String salaryRange) {
    	return jobPostingRepository.findAllJobPostingBySalaryRange(salaryRange);
	}

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
	
	
	//to get jobPosting in pages in Descending order
	public Page<JobPosting> getPaginatedJobPostingInDesc(int page, int size) {
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Order.desc("createdAt")));
		return jobPostingRepository.findByAvailable(true,pageable);
	}
	
	//to find jobPosting in pages 
	public Page<JobPosting> getPaginatedJobPostingByJobCategory(JobCategory jobCategory,int page,int size) {
		Pageable pageable = PageRequest.of(page, size);		
		return jobPostingRepository.findAllJobPostingByJobCategoryAndAvailable(jobCategory,true,pageable);
	}
	
	public Page<JobPosting> getPaginatedJobPostingByJobType(JobType jobType,int page,int size) {
		Pageable pageable = PageRequest.of(page, size);		
		return jobPostingRepository.findAllJobPostingByJobTypeAndAvailable(jobType,true,pageable);	
	}
	
	public Page<JobPosting> getPaginatedJobPostingByCityLocation(CityLocation location,int page,int size) {
		Pageable pageable = PageRequest.of(page, size);		
		return jobPostingRepository.findAllJobPostingByCityLocationAndAvailable(location,true,pageable);	
	}
	
	public Page<JobPosting> getPaginatedJobPostingByExpLevel(ExperienceLevel expLevel, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);		
		return jobPostingRepository.findAllJobPostingByExperienceLevelAndAvailable(expLevel,true,pageable);	
	}
	

	public Page<JobPosting> findAllJobPostingByKeyword(String keyword,int page,int size) {
		Pageable pageable = PageRequest.of(page, size);
    	return jobPostingRepository.findByTitleContainingOrSalaryRangeContainingOrEmployer_CompanyNameContainingAndAvailable(keyword, keyword, keyword,true, pageable);
	}
	
	public Integer countJobPostingOfSpecificJobCategory(JobCategory jobCategory) {
		return jobPostingRepository.countJobPostingByJobCategory(jobCategory);
	}
	
	//to count jobPostings
	public List<Integer> countJobPostingByJobCategory() {
		JobCategory[] types = JobCategory.values(); 
		ArrayList<Integer> countList = new ArrayList<>();

		for (JobCategory ty : types) {
			int count =  jobPostingRepository.countJobPostingByJobCategory(ty);
			countList.add(count);
		}
		return countList;
	}



	public List<Integer> countJobPostingOfJobType() {
		JobType[] types = JobType.values(); 
		ArrayList<Integer> countList = new ArrayList<>();
		
		for (JobType ty : types) {
			int count =  jobPostingRepository.countJobPostingByJobType(ty);
			countList.add(count);
		}
		return countList;

	}

	public List<Integer> countJobPostingOfExpType() {
		ExperienceLevel[] types = ExperienceLevel.values(); 
		ArrayList<Integer> countList = new ArrayList<>();
		
		for (ExperienceLevel ty : types) {
			int count =  jobPostingRepository.countJobPostingByExperienceLevel(ty);
			countList.add(count);
		}
		return countList;
	}

	public List<Integer> countJobPostingOfCityLocation() {
		CityLocation[] types = CityLocation.values(); 
		ArrayList<Integer> countList = new ArrayList<>();
		
		for (CityLocation ty : types) {
			int count =  jobPostingRepository.countJobPostingByCityLocation(ty);
			countList.add(count);
		}
		return countList;
	}


	
}
