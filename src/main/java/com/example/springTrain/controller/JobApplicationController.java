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

import com.example.springTrain.dto.JobSeekerDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;
import com.example.springTrain.entity.Users;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.FileStorageService;
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
	@Autowired
	private FileStorageService fileStorageService;
	
	
	//view the pdf png jpg jpeg webp file in browser
	@GetMapping("/view/application/file/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> viewUploadedFile(@PathVariable("fileName") String fileName) {
	    try {
	        Resource resource = fileStorageService.getFileAsResource(fileName);

	        // Determine file type based on the extension
	        String contentType = "application/octet-stream";
	        if (fileName.endsWith(".pdf")) {
	            contentType = "application/pdf";
	        }else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".webp")){
	        	//lastIndexOf includes . so add +1 
	        	//now file.jpg will be // jpg
	        	contentType = "image/" + fileName.substring(fileName.lastIndexOf('.') + 1);
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
	
	//viewing job applications and saved posts
	//view all lists of my JobPosts Applicants of an employer by employer
	//Employer login required
	//same employer can only see JobPosts Applicants
	@GetMapping("/view/applications/submittedto/employer/{employerId}/of/{jobId}")
	public String listAllJobApplicants(Model model,
			@PathVariable ("employerId") Long employerId,
			@PathVariable("jobId")Long jobId) {
					
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");
		Employer companyName = employerService.findByEmployerId(loggedInEmployerId);
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
		JobPosting jobPost = jobPostingService.getByJobId(jobId);
		
		model.addAttribute("jobPost",jobPost);	        
		model.addAttribute("allJobApplications",allJobApplications);	        
		return "application-all";
	}
	
	//view all lists of myJobPosts Applicants submitted by the jobseeker
	//viewing all aplications jobSeeker applied to 
	//JobSeekerlogin required
	@GetMapping("/view/application/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMyAppliedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Long jobSeekerId) {
			
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");
	
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		List<JobApplication> allJobApplicants = jobApplicationService.findAllJobApplicationByJobSeekerId(submittedJobSeeker.getJobSeekerId());
		
		if(!loggedInjobSeekerId.equals(jobSeekerId)) {	
        	logger.warn("Not a same jobSeekerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
	
		
		if(allJobApplicants != null) {
			//my applications deadline
		    List<String> applicationDeadline = jobApplicationService.getApplicationDeadlines(allJobApplicants);
		    
		    model.addAttribute("applicationDeadline", applicationDeadline);
		    model.addAttribute("allJobApplicants",allJobApplicants);	
		}	
		
		logger.info("Size of allJobApplicants list" + allJobApplicants.size());
		List<String> applicationDeadline = jobApplicationService.getApplicationDeadlines(allJobApplicants);
		logger.info("Size of applicationDeadline list:" + applicationDeadline.size());
		logger.info("Contents of applicationDeadline list: " + applicationDeadline);
		model.addAttribute("applicationDeadline", applicationDeadline);
		
		return "jobseeker/jobseeker-applications";
	}
	
	//view all lists of myJobPosts Saved submitted  by the jobseeker
	//viewing all aplications i Saved to 
	//login required
	@GetMapping("/view/savedjobs/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMySavedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Long jobSeekerId) {
					
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");

		JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
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

	//to apply for jobposts 
	@GetMapping("/applications/applyBy/{jobSeekerId}/to/{jobId}/{employerId}")
	public String showApplyForJobPosts(
	    @PathVariable("jobId") Long jobId,
	    @PathVariable("jobSeekerId") Long jobSeekerId,
	    @ModelAttribute("jobSeekerDTO") JobSeekerDTO jobSeekerDTO,
	    Model model) {
		
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");

		JobSeeker loggedInJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
	    JobPosting jobPost = jobPostingService.getJobPostingById(jobId);

	    model.addAttribute("jobPost", jobPost);
	    model.addAttribute("jobSeeker", loggedInJobSeeker);

	    if (jobSeekerDTO == null || jobSeekerDTO.getFullName() == null) {
	        // Populate DTO only if not passed from flash attributes
	        jobSeekerDTO = new JobSeekerDTO();
	        jobSeekerDTO.setFullName(loggedInJobSeeker.getFullName());
	        jobSeekerDTO.setEmail(loggedInJobSeeker.getUsers().getEmail());
	        jobSeekerDTO.setNumber(loggedInJobSeeker.getNumber());
	    }

	    model.addAttribute("jobSeekerDTO", jobSeekerDTO);

	    return "jobseeker/jobseeker-apply";
	}

	//to apply for jobposts of employer
	//jobposts id required
	//jobseekerId required
	//make changes to jobApplication
	@PostMapping("/applications/applyBy/{jobSeekerId}/to/{jobId}/{employerId}")
	public String applyForJobPosts(Model model,
					@PathVariable("jobSeekerId") Long jobSeekerId,
					@PathVariable("jobId") Long jobId,
					@PathVariable("employerId") Long employerId,
					@ModelAttribute("jobseekerDTO") JobSeekerDTO jobseekerDTO,
					@RequestParam("file")MultipartFile file,
					RedirectAttributes redirectAttributes) {
	     
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");

		// Validate file type
	    Boolean validFileType = fileStorageService.checkFileType(file);
	    if(!validFileType) {
	        redirectAttributes.addFlashAttribute("errorMessage", "File type needs to be in .pdf format");
	        return "redirect:/applications/applyBy/"+ jobSeekerId + "/to/"+jobId+"/"+ employerId ;
	    }
	      
		JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
		JobApplication jobApplication = jobApplicationService.getJobApplicationByJobIdAndJobSekerId(jobId,jobSeekerId); 
		Employer employer = employerService.findByEmployerId(employerId); 
		Users user = userService.findByEmployer_employerId(employerId); 
			
		if(!loggedinJobSeeker.equals(submittedJobSeeker)) {
			logger.warn("Not same jobSeeker so cannot apply");
			return "redirect:/view/jobposts";
		}
         if(employer == null ||jobPosting == null || user == null || jobApplication != null || jobseekerDTO == null) {
 			logger.warn("Unable  to apply to jobPosts ");
            return "redirect:/view/jobposts";
         }      
        	 
      
        jobApplicationService.applyForJobPost(jobId,employerId,jobSeekerId,jobseekerDTO,file);
        
        // Send a notification to the employer
        String message = "You have a new application for the job: " + jobPosting.getTitle();
        notificationService.createNotification(user, message);
        
        redirectAttributes.addFlashAttribute("successMessage", "Application status has been updated successfully!");

    	return "redirect:/view/jobposts/details/" + jobId;
	}
	
	
	// Update application status for a specific job application of jobSeeker by employer
	//send application reviewed notification by employer to jobSeeker
	@PostMapping("/application/submittedto/employer/{employerId}/statusUpdate/{applicationId}")
	public String updateApplicationStatusByEmployer(
		@RequestParam("applicationStatus") String applicationStatus,
	    @PathVariable("employerId") Long employerId,
	    @PathVariable("applicationId") Long applicationId,
	    RedirectAttributes redirectAttributes) {
		

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
		
		redirectAttributes.addFlashAttribute("successMessage", "Your application has been submitted successfully!");
	    // Redirect back to the list of job applications of the specific employerId
	    return "redirect:/view/applications/submittedto/employer/" + employerId +"/of/" + jobApplication.getJobPosting().getJobId();
	
	}
	
	
	//Save jobPost by JobSeeker 
		//needs JobSeeker to login
		@PostMapping("/saveBy/jobSeeker/{jobSeekerId}/jobPost/{jobId}")
		public String saveJobsByJobSeeker(Model model,
				@PathVariable("jobSeekerId") Long jobSeekerId,
				@PathVariable("jobId") Long jobId,
			    RedirectAttributes redirectAttributes) {
			
			Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");

			JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
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
				redirectAttributes.addFlashAttribute("successMessage", "Job  has been saved successfully!");

			return "redirect:/view/jobposts/details/" +jobId;
		}
		
		@PostMapping("/unsaveBy/jobSeeker/{jobSeekerId}/jobPost/{jobId}")
		public String unSaveJobsByJobSeeker(Model model,
				@PathVariable("jobSeekerId") Long jobSeekerId,
				@PathVariable("jobId") Long jobId,
			    RedirectAttributes redirectAttributes,
				@RequestParam(value = "fromApplications", required = false, defaultValue = "false")boolean fromApplications) {
				       
			Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");

			JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
			JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
			JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
			SavedJobs alreadySaved = savedJobsService.findByJobPosting_JobIdAndJobSeekerId(jobId,jobSeekerId);
			
			if(!loggedInjobSeekerId.equals(jobSeekerId)) {
				return "redirect:/view/jobposts";
			}
	         if(submittedJobSeeker == null|| jobPosting == null || alreadySaved == null) {
	   			logger.warn(" Unable to save jobPost");
	            return "redirect:/view/jobposts";
	         }   
	         
			 savedJobsService.unSaveJobForJobSeeker(jobPosting,loggedinJobSeeker);
			 
			 //redirecting conditonally
			 if (fromApplications) {
					redirectAttributes.addFlashAttribute("successMessage", "Job  has been Un-Saved!");
		            return "redirect:/view/savedjobs/submittedby/jobseeker/" + jobSeekerId;
		        } else {
					redirectAttributes.addFlashAttribute("successMessage", "Job  has been Un-Saved!");
		            return "redirect:/view/jobposts/details/" + jobId;
		        }
		}
		
		
		@PostMapping("/application/{applicationId}/withdraw-by/jobseeker/{jobSeekerId}/to-jobpost/{jobId}/of/{employerId}")
		public String withdrawApplicationOfJobPost(Model model,
						@PathVariable("jobSeekerId") Long jobSeekerId,
						@PathVariable("jobId") Long jobId,
						@PathVariable("employerId") Long employerId,
						@PathVariable("applicationId") Long applicationId,
						@RequestParam(value = "fromApplications", required = false, defaultValue = "false")boolean fromApplications,
						RedirectAttributes redirectAttributes){
		     
			Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");

			JobSeeker loggedinJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
			JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
			JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
			JobApplication jobApplication = jobApplicationService.getJobApplicationByJobIdAndJobSekerId(jobId,jobSeekerId); 
			Employer employer = employerService.findByEmployerId(employerId); 
			Users user = userService.findByEmployer_employerId(employerId); 
				
			if(!loggedinJobSeeker.equals(submittedJobSeeker)) {
				logger.warn("Not same jobSeeker so cannot apply");
				return "redirect:/view/jobposts";
			}
			
			try {
	         if(employer == null ||jobPosting == null || user == null || jobApplication == null) {
	 			logger.warn("Unable  to withdraw application to jobPost ");
	            return "redirect:/view/jobposts";
	         } 
	        
				jobApplicationService.withdrawApplicationByJobSeeker(jobId,employerId,jobSeekerId);
				   // Send a notification to the employer
		        String message = "JobSeeker"+ loggedinJobSeeker.getFullName() + " has withdrawn application for the job: " + jobPosting.getTitle();
		        notificationService.createNotification(user, message);
		        redirectAttributes.addFlashAttribute("successMessage", "Your application has been withdrawn successfully!");

			} catch (Exception e) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Your application was not found to be withdrawn!");
				e.printStackTrace();
			}
	          
			//redirecting conditional
			if(fromApplications) {
				redirectAttributes.addFlashAttribute("successMessage", "Job application has been withdrawn!");

				return "redirect:/view/application/submittedby/jobseeker/" + jobSeekerId ;
			}else {
				redirectAttributes.addFlashAttribute("successMessage", "Job application has been withdrawn!");

				return "redirect:/view/jobposts/details/" + jobId;
			}
		}
		
}