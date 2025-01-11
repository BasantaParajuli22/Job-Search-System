package com.example.springTrain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springTrain.dto.JobSeekerDTO;
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
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JobApplicationRepository jobApplicationRepository;	
	private final JobSeekerRepository jobSeekerRepository;
	private final JobPostingRepository jobPostingRepository;
	private final EmployerRepository employerRepository;
	private final FileStorageService fileStorageService;
	
	
	@Autowired
	public JobApplicationService(
			JobApplicationRepository jobApplicationRepository,
			JobSeekerRepository jobSeekerRepository,
			JobPostingRepository jobPostingRepository,
			EmployerRepository employerRepository, 
			FileStorageService fileStorageService
			) {
		this.jobApplicationRepository =jobApplicationRepository;
		this.jobSeekerRepository =jobSeekerRepository;
		this.jobPostingRepository =jobPostingRepository;
		this.employerRepository =employerRepository;
		this.fileStorageService =fileStorageService;

	}

	@Transactional
	public void applyForJobPost(Long jobId, Long employerId, Long jobSeekerId,
			JobSeekerDTO jobseekerDTO, MultipartFile filename) {
		
		String filePath = fileStorageService.saveFile(filename);

		JobApplication jobApplication = new JobApplication();		
		
		JobSeeker jobSeeker = jobSeekerRepository.findByJobSeekerId(jobSeekerId);
		JobPosting jobPosting = jobPostingRepository.findByJobId(jobId);
		Employer employer = employerRepository.findByEmployerId(employerId);
		
		
		jobApplication.setJobSeeker(jobSeeker);
		jobApplication.setJobPosting(jobPosting);
		jobApplication.setEmployer(employer);
		jobApplication.setApplicationStatus("not-viewed");
		
		jobApplication.setEmail(jobseekerDTO.getEmail());
		jobApplication.setNumber(jobseekerDTO.getNumber());
		jobApplication.setFullName(jobseekerDTO.getFullName());
		
		jobApplication.setFilePath(filePath);
		
		jobApplicationRepository.save(jobApplication);

	}
	
	//after changing status saving jobApplication	
	@Transactional
	public void save(JobApplication jobApplication) {
	    jobApplicationRepository.save(jobApplication);
	}
		
	//finding applicationId and setting its status and saving it
	@Transactional
	public void updateStatus(Long applicationId, String applicationStatus) {
		//if matches setting new Status and saving
		JobApplication application = jobApplicationRepository.findByApplicationId(applicationId);
	    application.setApplicationStatus(applicationStatus);
		jobApplicationRepository.save(application);
	}
	
	@Transactional
    public void withdrawApplicationByJobSeeker(Long jobId, Long employerId, Long jobSeekerId)throws Exception {
	    JobApplication jobApplication = jobApplicationRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobId, jobSeekerId);

	    if (jobApplication == null) {
	    	throw new Exception("Job application not found for Job ID: " + jobId + ", Job Seeker ID: " + jobSeekerId + " and employerId: "+ employerId);
	    }
	    jobApplicationRepository.delete(jobApplication);
	}
	
	//return a list of jobApplication deadlines in days
	public List<String> getApplicationDeadlines(List<JobApplication> allJobApplicants) {
        List<String> dates = new ArrayList<>();
        
        for (JobApplication job : allJobApplicants) {
            
            if(job == null) {
                
                 dates.add("No Deadline Found");
                 continue;
            }
            if(job.getJobPosting() == null) {
                
                dates.add("No Deadline Found");
                continue;
            }
             LocalDate deadline = job.getJobPosting().getApplicationDeadline();
              
           if (deadline == null) {
                
                dates.add("No Deadline Set");
            } else if (deadline.isBefore(LocalDate.now())) {
                  
                dates.add("Time is up");
            } else {
                 
                Long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
                String totaldays = String.format("%d days", daysLeft); // Use String.format
                dates.add(totaldays);
            }

        }
        return dates;
    }

	
	//find all jobPosts by employerId
	//find their all jobApplications by jobId 	
	public List<Long> countTotalApplicantsOfEmployer(Long employerId) {
		
		List<JobPosting> jobPosts = jobPostingRepository.findByEmployer_EmployerId(employerId);		
		List<Long> countList = new ArrayList<>();
		
		for(JobPosting jobPost :jobPosts) {	
			Long jobId = jobPost.getJobId();
			Long jobAppCount = jobApplicationRepository.countJobApplicationByJobPosting_JobId(jobId);
			countList.add(jobAppCount);
		}
		
		return countList;
	}
	
	//to find whether there is same jobId and jobSeekerId
	//if found u cannot apply again
	public JobApplication getJobApplicationByJobIdAndJobSekerId(Long jobId, Long jobSeekerId) {
		return jobApplicationRepository.findByJobPosting_JobIdAndJobSeeker_JobSeekerId(jobId,jobSeekerId);
	} 
	
	public JobApplication findById(Long applicationId) {
		return jobApplicationRepository.findByApplicationId(applicationId);
	}
	
	public List<JobApplication> findAllJobApplicationByJobSeekerId(Long jobSeekerId) {
		return jobApplicationRepository.findByJobSeeker_JobSeekerId(jobSeekerId);
	}
	
	public List<JobApplication> findAllJobApplicationByEmployerIdAndJobId(Long employerId, Long jobId) {
		return jobApplicationRepository.findByEmployer_EmployerIdAndJobPosting_JobId(employerId,jobId);
	}




 


}
