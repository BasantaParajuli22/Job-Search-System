package com.example.springTrain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;
import com.example.springTrain.entity.Users;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.NotificationService;
import com.example.springTrain.service.SavedJobsService;
import com.example.springTrain.service.UsersService;

@Controller
public class JobApplicationController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private SavedJobsService savedJobsService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UsersService userService;
	
	
	//viewing job applications and saved posts
	//view all lists of my JobPosts Applicants of an employer by employer
	//Employer login required
	//same employer can only see JobPosts Applicants
	@GetMapping("/view/applications/submittedto/employer/{employerId}/of/{jobId}")
	public String listAllJobApplicants(Model model,
			@PathVariable ("employerId") Integer employerId,
			@PathVariable("jobId")Integer jobId,
			@ModelAttribute("employer") Employer employer) {
					
		Employer companyName = employerService.findByCompanyName(employer.getCompanyName());
		Employer submittedCompanyName = employerService.findByEmployerId(employerId);
		if(!companyName.equals(submittedCompanyName)) {	
        	logger.warn("Not a same employerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
		List<JobApplication> allJobApplications = jobApplicationService.findAllJobApplicationByEmployerIdAndJobId(companyName.getEmployerId(),jobId);
		if(allJobApplications == null) {
        	logger.warn("Couldnot find the jobApplications");
			return "redirect:/view/jobposts";
		}
		model.addAttribute("allJobApplications",allJobApplications);	        
		return "application-all";
	}
	
	//view the pdf file in browser
	@GetMapping("/view/application/file/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> viewUploadedFile(@PathVariable("fileName") String fileName) {
	    try {
	        Resource resource = jobApplicationService.getFileAsResource(fileName);

	        // Determine file type based on the extension
	        String contentType = "application/octet-stream";
	        if (fileName.endsWith(".pdf")) {
	            contentType = "application/pdf";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                // Use inline to display in browser if supported
	                ////Content-Disposition: inline; filename="example.txt"
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
	                .body(resource);
	    } catch (RuntimeException e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	
	//view all lists of myJobPosts Applicants submitted by the jobseeker
	//viewing all aplications jobSeeker applied to 
	//JobSeekerlogin required
	@GetMapping("/view/application/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMyAppliedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Integer jobSeekerId,
			@ModelAttribute("jobSeeker") JobSeeker jobSeeker) {
			
		JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(jobSeeker.getJobSeekerId());
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		List<JobApplication> allJobApplicants = jobApplicationService.findAllJobApplicationByJobSeekerId(loggedinJobSeeker.getJobSeekerId());
		
		if(!loggedinJobSeeker.equals(submittedJobSeeker)) {	
        	logger.warn("Not a same jobSeekerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
		if(allJobApplicants != null) {
			//my applications deadline
		    List<String> applicationDeadline = jobApplicationService.getApplicationDeadlines(allJobApplicants);
		    
		    model.addAttribute("applicationDeadline", applicationDeadline);
		    model.addAttribute("allJobApplicants",allJobApplicants);	
		}		
		return "jobseeker/jobseeker-applications";
	}
	
	//view all lists of myJobPosts Saved submitted  by the jobseeker
	//viewing all aplications i Saved to 
	//login required
	@GetMapping("/view/savedjobs/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMySavedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Integer jobSeekerId,
			@ModelAttribute("jobSeeker") JobSeeker jobSeeker) {
					
		JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(jobSeeker.getJobSeekerId());
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		List<SavedJobs> savedPosts = savedJobsService.findAllSavedJobsByJobSeeker(loggedinJobSeeker);
		
		if(!loggedinJobSeeker.equals(submittedJobSeeker)) {	
        	logger.warn("Not a same jobSeekerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
		if(savedPosts != null) {
			//my savedJobs deadline
		    List<String> savedDeadline = savedJobsService.getSavedJobsDeadlines(savedPosts);
		    model.addAttribute("savedDeadline", savedDeadline);
		    model.addAttribute("savedPosts",savedPosts);	        
		}
		return "jobseeker/jobseeker-applications";
	}

	
	//to apply for jobposts of employer
	//jobposts id required
	//jobseekerId required
	//make changes to jobApplication
	@PostMapping("/applications/applyBy/{jobSeekerId}/to/{jobId}/{employerId}")
	public String applyForJobPosts(Model model,
					@PathVariable("jobSeekerId") Integer jobSeekerId,
					@PathVariable("jobId") Integer jobId,
					@PathVariable("employerId") Integer employerId,
					@RequestParam("cv")MultipartFile file,
					@ModelAttribute("jobSeeker") JobSeeker jobSeeker,
					RedirectAttributes redirectAttributes) {
	     
    	System.out.println("more than 2 mb or not");

		// Validate file type
	    String contentType = file.getContentType();
	    if (contentType == null || !contentType.equals("application/pdf")) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Invalid file type. Please upload a valid PDF.");
	    	return "redirect:/view/jobposts/details/" +jobId;
	    }
	    // Check file size (2 MB limit)
	    if (file.getSize() > 2 * 1024 * 1024) {  // 2 MB in bytes
	    	System.out.println("more than 2 mb");
	        redirectAttributes.addFlashAttribute("errorMessage", "File size exceeds the maximum allowed size of 2 MB.");
	        return "redirect:/view/jobposts/details/" + jobId;
	    }
	    
		JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(jobSeeker.getJobSeekerId());
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
		JobApplication jobApplication = jobApplicationService.getJobApplicationByJobIdAndJobSekerId(jobId,jobSeekerId); 
		Employer employer = employerService.findByEmployerId(employerId); 
		Users user = userService.findByEmployer_employerId(employerId); 
		
		
		if(!loggedinJobSeeker.equals(submittedJobSeeker)) {
			logger.warn("Not same jobSeeker so cannot apply");
			return "redirect:/view/jobposts";
		}
         if(employer == null ||jobPosting == null || user == null || jobApplication != null) {
 			logger.warn("Unable  to apply to jobPosts ");
            return "redirect:/view/jobposts";
         }      
        	 
        String filename = jobApplicationService.saveFile(file);
        jobApplicationService.applyForJobPost(jobId,employerId,jobSeekerId,filename);
        
        // Send a notification to the employer
        String message = "You have a new application for the job: " + jobPosting.getTitle();
        notificationService.createNotification(user, message);
        
        redirectAttributes.addFlashAttribute("successMessage", "Your application has been submitted successfully!");

    	return "redirect:/view/jobposts/details/" +jobId;
	}
	
	
	// Update application status for a specific job application of jobSeeker by employer
	//send application reviewed notification by employer to jobSeeker
	@PostMapping("/application/submittedto/employer/{employerId}/statusUpdate/{applicationId}")
	public String updateApplicationStatusByEmployer(
		@RequestParam("applicationStatus") String applicationStatus,
	    @PathVariable("employerId") Integer employerId,
	    @PathVariable("applicationId") Integer applicationId) {
		

		Employer employer = employerService.findByEmployerId(employerId);
		JobApplication jobApplication = jobApplicationService.findById(applicationId);
		Users jobSeekerUser = jobApplication.getJobSeeker().getUsers();
		
		if(jobApplication== null || jobSeekerUser == null || employer == null) {
			logger.warn("application or jobseeker or employer not found");
			return"login";
		}

		//notification message and creating message
        String message = "Your application for the jobPost:"+ 
        		jobApplication.getJobPosting().getTitle() + " has been reviewed:"+ "Status: " +
        		applicationStatus +"by"+ employer.getCompanyName();

        notificationService.createNotification(jobSeekerUser, message); 
		jobApplicationService.updateStatus(applicationId,applicationStatus);
		
	    // Redirect back to the list of job applications of the specific employerId
	    return "redirect:/view/applications/submittedto/employer/" + employerId +"/of/" + jobApplication.getJobPosting().getJobId();
	
	}
	
	
	//Save jobPost by JobSeeker 
		//needs JobSeeker to login
		@PostMapping("/saveBy/jobSeeker/{jobSeekerId}/jobPost/{jobId}")
		public String saveJobsByJobSeeker(Model model,
				@PathVariable("jobSeekerId") Integer jobSeekerId,
				@PathVariable("jobId") Integer jobId,
				@ModelAttribute("jobSeeker") JobSeeker jobSeeker) {
				       
			JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(jobSeeker.getJobSeekerId());
			JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
			JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
			SavedJobs alreadySaved= savedJobsService.findByJobPosting_JobIdAndJobSeekerId(jobId,jobSeekerId);
			
			if(!loggedinJobSeeker.equals(submittedJobSeeker)) {
				return "redirect:/view/jobposts";
			}
	         if(submittedJobSeeker == null|| jobPosting == null || alreadySaved != null) {
	   			logger.warn(" Unable to save jobPost");
	            return "redirect:/view/jobposts";
	         }   
	         
			 savedJobsService.saveJobForJobSeeker(jobPosting,loggedinJobSeeker);
			return "redirect:/view/jobposts/details/" +jobId;
		}
		
		@PostMapping("/unsaveBy/jobSeeker/{jobSeekerId}/jobPost/{jobId}")
		public String unSaveJobsByJobSeeker(Model model,
				@PathVariable("jobSeekerId") Integer jobSeekerId,
				@PathVariable("jobId") Integer jobId,
				@ModelAttribute("jobSeeker") JobSeeker jobSeeker) {
				       
			JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(jobSeeker.getJobSeekerId());
			JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
			JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
			SavedJobs alreadySaved= savedJobsService.findByJobPosting_JobIdAndJobSeekerId(jobId,jobSeekerId);
			
			if(!loggedinJobSeeker.equals(submittedJobSeeker)) {
				return "redirect:/view/jobposts";
			}
	         if(submittedJobSeeker == null|| jobPosting == null || alreadySaved == null) {
	   			logger.warn(" Unable to save jobPost");
	            return "redirect:/view/jobposts";
	         }   
	         
			 savedJobsService.unSaveJobForJobSeeker(jobPosting,loggedinJobSeeker);
			return "redirect:/view/jobposts/details/" +jobId;
		}
		
		
}
