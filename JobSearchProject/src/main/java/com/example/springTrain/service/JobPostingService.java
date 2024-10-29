package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.repository.JobPostingRepository;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobPosting;

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
    
    //finding list of companyName in JobPosting table
	public List<JobPosting> findByEmployerCompanyName(String companyName) {
        return jobPostingRepository.findByEmployerCompanyName(companyName);

	}
	
	//to find all jobPostings of employer using CompanyName
	public List<JobPosting> findAllJobPostingsByEmployer_CompanyName(String companyName) {
    	return jobPostingRepository.findAllJobPostingsByEmployer_CompanyName(companyName);

	}
    

}
