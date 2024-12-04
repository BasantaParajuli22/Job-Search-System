package com.example.springTrain.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;
import com.example.springTrain.entity.Users;
import com.example.springTrain.security.UserAuthorization;
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

	
	// adding jobSeeker to the model if a user is logged in.
    @ModelAttribute
    public void addJobSeekerToModel(Model model) {
    	//getting LoggedInJobSeekerUsername in string
        String username = UserAuthorization.getLoggedInJobSeekerUsername();
        if (username != null) {//if found finding in jobSeeker entity
            JobSeeker jobSeeker = jobSeekerService.findByUsername(username);
            if (jobSeeker != null) {//if found adding to model
                model.addAttribute("jobSeeker", jobSeeker);
            } 
        }
    }
    
    @ModelAttribute
    public void addEmployerToModel(Model model) {
    	//getting loggedinEmployerUsername in string
        String username = UserAuthorization.getLoggedInEmployerUsername();
        if (username != null) {//if found finding in employer entity
            Employer employer = employerService.findByCompanyName(username);
            if (employer != null) {//if found adding to model
                model.addAttribute("employer", employer);
            } 
        }
    }
    
	//to apply for jobposts of employer
	//jobposts id required
	//jobseekerId required
	//make changes to jobApplication
	@PostMapping("/applications/applyBy/{jobSeekerId}/to/{jobId}/{companyName}")
	public String applyForJobPosts(Model model,
					@PathVariable("jobSeekerId") Integer jobSeekerId,
					@PathVariable("jobId") Integer jobId,
					@PathVariable("companyName") String companyName) {
	    

   	 	// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
       
		JobSeeker loggedJobSeeker = jobSeekerService.findByUsername(username);
		JobSeeker submittedUsername = jobSeekerService.findByJobSeekerId(jobSeekerId);

		if(!loggedJobSeeker.equals(submittedUsername)) {
			logger.warn("Not same jobSeeker so cannot apply");
			return "redirect:/view/jobposts";
		}

		//if employer or jobposting not found // exit //
        Employer employer = employerService.findByCompanyName(companyName); 
        JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
         if(employer == null ||jobPosting == null) {
 			logger.warn("Not same employer or jobId");
            return "redirect:/view/jobposts";
         }  
         //only apply if jobSeeker hasnot applied already
         //only one jobSeekerId to one jobId
         JobApplication jobApplication = jobApplicationService.getJobSeekerByIdAndJobId(jobSeekerId,jobId); 
         if(jobApplication != null) {
  			logger.warn(" Same JobSeeker cannot apply again to jobpost ");
             return "redirect:/view/jobposts";
          }
         
         jobApplicationService.applyForJobPost(jobId,companyName,jobSeekerId);
        
        // Send a notification to the employer
        String message = "You have a new application for the job: " + jobPosting.getTitle();
        Users user = userService.findByUsername(companyName); //finding companyName in User entity
        if(user == null) {
        	logger.warn("no user found to send notification");
        	return"login";
        }
        System.out.println("now go to create notification");
        notificationService.createNotification(user, message);

    	return "redirect:/view/jobposts";

	}
	
	//Save jobPost by JobSeeker 
	//needs JobSeeker to login
	@PostMapping("/saveBy/jobSeeker/{jobSeekerId}/jobPost/{jobId}")
	public String saveJobsByJobSeeker(Model model,
			@PathVariable("jobSeekerId") Integer jobSeekerId,
			@PathVariable("jobId") Integer jobId) {
		
		// Get the currently logged-in user's username
		String username = UserAuthorization.getLoggedInUsername();
		       
		JobSeeker loggedJobSeeker = jobSeekerService.findByUsername(username);
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		JobPosting jobPosting = jobPostingService.getJobPostingById(jobId); 
		if(!loggedJobSeeker.equals(submittedJobSeeker)) {
			return "redirect:/view/jobposts";
		}

		//ifsubmittedJobSeeker is null then// exit //
         if(submittedJobSeeker == null|| jobPosting == null ) {
   			logger.warn(" jobSeeker not found or jobPost not found to save");
            return "redirect:/view/jobposts";
         }   
         
         SavedJobs alreadySaved= savedJobsService.findByJobPosting_JobIdAndJobSeekerId(jobId,jobSeekerId);
         if(alreadySaved != null) {
    		logger.info(" JobPost already saved by JobSeeker");
             return "redirect:/view/jobposts";
         }
         
		 savedJobsService.saveJobForJobSeeker(jobPosting,loggedJobSeeker);
		return "redirect:/view/jobposts";
	}
	
	// Update application status for a specific job application of jobSeeker by employer
	//send application reviewed notification by employer to jobSeeker
	@PostMapping("/applications/submittedto/employer/statusUpdate")
	public String updateApplicationStatusByEmployer(
	    @RequestParam("applicationId") Integer applicationId,
	    @RequestParam("applicationStatus") String applicationStatus,
	    @RequestParam("employerId") Integer employerId) {
		
		//calling to save changed status 
		jobApplicationService.updateStatus(applicationId,applicationStatus);
		
		JobApplication jobApplication = jobApplicationService.findById(applicationId);
		if(jobApplication== null) {
			logger.warn("jobApplication couldnot be found");
			return"login";
		}
		
		// Send a notification to the jobSeeker by employer
		//get the jobApplication jobseeker users entity for notification
        Users jobSeekerUser = jobApplication.getJobSeeker().getUsers();
        if(jobSeekerUser == null) {
        	logger.warn("jobSeekerUser couldnot be found");
			return"login";
        }
        Employer employer = employerService.findByEmployerId(employerId);
        if(employer == null) {
        	logger.warn("employer couldnot be found");
			return"login";
        }
        String message = "Your application for the jobPost:" + jobApplication.getJobPosting().getTitle() +" has been reviewed:"
        		+ "Status: " + applicationStatus +"by"+ employer.getCompanyName();

        notificationService.createNotification(jobSeekerUser, message);
	    // Redirect back to the list of job applications of the specific employerId
	    return "redirect:/view/applications/submittedto/employer/" + employerId;
	}
	
	
	
	
	
	//viewing job applications and saved posts
	//view all lists of my JobPosts Applicants of an employer
	//login required
	//same employer can only see JobPosts Applicants
	@GetMapping("/view/applications/submittedto/employer/{employerId}/of/{jobId}")
	public String listAllJobApplicants(Model model,
			@PathVariable ("employerId") Integer employerId,
			@PathVariable("jobId")Integer jobId) {
			
		String username = UserAuthorization.getLoggedInUsername();
		
		Employer companyName = employerService.findByCompanyName(username);
		Employer submittedCompanyName = employerService.findByEmployerId(employerId);
		if(!companyName.equals(submittedCompanyName)) {	
        	logger.warn("Not a same employerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
		 List<JobApplication> allJobApplications = jobApplicationService.findAllJobApplicationByEmployerAndJobId(companyName,jobId);
		if(allJobApplications == null) {
        	logger.warn("Couldnot find the jobApplications");
			return "redirect:/view/jobposts";
		}
		 model.addAttribute("allJobApplications",allJobApplications);	        
		return "application-all";
	}
	
	//view all lists of myJobPosts Applicants submitted  by the jobseeker
	//viewing all aplications i applied to 
	//login required
	@GetMapping("/view/application/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMyAppliedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Integer jobSeekerId) {
			
		String username = UserAuthorization.getLoggedInUsername();
		JobSeeker loggedinJobSeeker = jobSeekerService.findByUsername(username);
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		if(!loggedinJobSeeker.equals(submittedJobSeeker)) {	
        	logger.warn("Not a same jobSeekerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
		List<JobApplication> allJobApplicants = jobApplicationService.findAllJobApplicationByJobSeeker(loggedinJobSeeker);
		if(allJobApplicants == null) {
        	logger.warn("Couldnot find the jobApplications ");
			return "redirect:/view/jobposts";
		}
		if(allJobApplicants != null) {
			model.addAttribute("allJobApplicants",allJobApplicants);	
			//my applications deadline
		    List<String> applicationDeadline = jobApplicationService.getApplicationDeadlines(allJobApplicants);
		    model.addAttribute("applicationDeadline", applicationDeadline);
		}		

		return "jobseeker-applications";
	}
	
	// Update application status for a specific job application
	@PostMapping("/view/applications/submittedto/employer/statusUpdate")
	public String postUpdateApplicationStatus(
	    @RequestParam("applicationId") Integer applicationId,
	    @RequestParam("applicationStatus") String applicationStatus,
	    @RequestParam("employerId") Integer employerId) {
		
		//calling to save changed status 
		jobApplicationService.updateStatus(applicationId,applicationStatus);
	    
	    // Redirect back to the list of job applications for the specific employer
	    return "redirect:/view/applications/submittedto/employer/" + employerId;
	}


	
	//view all lists of myJobPosts Saved submitted  by the jobseeker
	//viewing all aplications i Saved to 
	//login required
	@GetMapping("/view/savedjobs/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMySavedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Integer jobSeekerId) {
			
		String username = UserAuthorization.getLoggedInUsername();
		
		JobSeeker loggedinJobSeeker = jobSeekerService.findByUsername(username);
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		if(!loggedinJobSeeker.equals(submittedJobSeeker)) {	
        	logger.warn("Not a same jobSeekerId so cannot view jobApplicants ");
			return "redirect:/view/jobposts";
		}
		
		List<SavedJobs> savedPosts = savedJobsService.findAllSavedJobsByJobSeeker(loggedinJobSeeker);
		if(savedPosts == null) {
        	logger.warn("Couldnot find the savedJobs ");
			return "redirect:/view/jobposts";
		}
		if(savedPosts != null) {
			model.addAttribute("savedPosts",savedPosts);	        
			//my savedJobs deadline
		    List<String> savedDeadline = savedJobsService.getSavedJobsDeadlines(savedPosts);
		    model.addAttribute("savedDeadline", savedDeadline);
		}
		return "jobseeker-applications";
	}

	
	
	//cv upload
//	@GetMapping("/upload/files")
//	public String uploadFilesFormByJobSeeker(Model model) {
//		
//		return "";
//	}
}
