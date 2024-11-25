package com.example.springTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.repository.EmployerRepository;
import com.example.springTrain.repository.JobApplicationRepository;
import com.example.springTrain.repository.JobPostingRepository;
import com.example.springTrain.repository.JobSeekerRepository;

@Service
public class JobApplicationService {
	
	private JobApplicationRepository jobApplicationRepository;	
	private JobSeekerRepository jobSeekerRepository;
	private JobPostingRepository jobPostingRepository;
	private EmployerRepository employerRepository;
	
	
	@Autowired
	public JobApplicationService(
			JobApplicationRepository jobApplicationRepository,
			JobSeekerRepository jobSeekerRepository,
			JobPostingRepository jobPostingRepository,
			EmployerRepository employerRepository
			) {
		this.jobApplicationRepository =jobApplicationRepository;
		this.jobSeekerRepository =jobSeekerRepository;
		this.jobPostingRepository =jobPostingRepository;
		this.employerRepository =employerRepository;
	}

	//takes jobId companyName and jobSeekerId 
	//stores in jobApplication 
	public void applyForJobPost(Integer jobId, String companyName, Integer jobSeekerId) {
		
		//new jobApplication object 
		JobApplication jobApplication = new JobApplication();
		
		//Fetching Entities: You first fetch the JobSeeker and JobPosting and Employer entities 
		//from their respective repositories 
		//This ensures you are working with valid references to those objects,
		//as they already exist in the database
		JobSeeker jobSeeker = jobSeekerRepository.findByJobSeekerId(jobSeekerId);
		JobPosting jobPosting = jobPostingRepository.findByJobId(jobId);
		Employer employer = employerRepository.findByCompanyName(companyName);

		//to link the job application to the respective job seeker and
		//jobPost  and employer in the database.
		jobApplication.setJobSeeker(jobSeeker);
		jobApplication.setJobPosting(jobPosting);
		jobApplication.setEmployer(employer);
		//saving jobApplication to repository
		jobApplicationRepository.save(jobApplication);

	}

	//to find whether there is same jobId and jobSeekerId
	//if found u cannot apply again
	public JobApplication getJobSeekerByIdAndJobId(Integer jobSeekerId,Integer jobId) {
		// TODO Auto-generated method stub
		return jobApplicationRepository.findByJobSeeker_JobSeekerIdAndJobPosting_JobId(jobSeekerId,jobId);
	}

	//find all jobPosts applications submitted by jobseeker
	public List<JobApplication> findAllJobApplicationByJobSeeker(JobSeeker jobSeeker) {
		String jobSeekerUsername = jobSeeker.getJobSeekerUsername();
		return jobApplicationRepository.findAllJobApplicationByJobSeeker_JobSeekerUsername(jobSeekerUsername);

	}

	public List<JobApplication> findAllJobApplicationByEmployer(Employer employer) {
		String companyName = employer.getCompanyName();
		return jobApplicationRepository.findAllJobApplicationByEmployer_CompanyName(companyName);

	}

	public JobApplication findById(Integer applicationId) {
		return jobApplicationRepository.findByApplicationId(applicationId);
	}
	
	//after changing status saving jobApplication	
	public void save(JobApplication jobApplication) {
        jobApplicationRepository.save(jobApplication);
    }
	
	//finding applicationId and setting its status and saving it
	public void updateStatus(Integer applicationId, String applicationStatus) {
		//if matches setting new Status and saving
		JobApplication application = jobApplicationRepository.findByApplicationId(applicationId);
        application.setApplicationStatus(applicationStatus);
		jobApplicationRepository.save(application);
	}
}
