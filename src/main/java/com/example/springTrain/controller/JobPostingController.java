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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.Users;
import com.example.springTrain.enums.Usertype;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.NotificationService;
import com.example.springTrain.service.UsersService;

@Controller
@RequestMapping("/jobposts")
public class JobPostingController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private NotificationService notificationService;
	
	//to create a jobposting 
	//user must be authenticated and should be employer
	@GetMapping("/new/create")
	public String getJobPostForm(Model model,
			@ModelAttribute("employer") Employer employer) {
	     	 
         if(employer == null) {
        	 logger.warn("User not available to create jobPosts");
            return "login";
         }
        model.addAttribute("employer", employer);
        model.addAttribute("jobPosting", new JobPosting());  // Pass a new JobPosting object
        return "jobpostform";
	}
	
	//for posting jobpost employerId is needed
	//employerId is set in joposting 
	//and jobposting is created
	@PostMapping("/new/create")
	public String createJobPostForm(@ModelAttribute JobPosting jobPosting,
									@RequestParam("employerId") Integer employerId,
									@ModelAttribute("employer") Employer employer) {
		
	    if (employer == null) {
       	 logger.warn("User not suitable for posting jobposting");
        	return "login";
	    }

		jobPostingService.createJobPosting(jobPosting,employer);//jobposting created
		return "redirect:/view/jobposts";
	}
	
	
	//DELETE 
	//by ALL
	//all jobposting can be deleted by all
    @PostMapping("/{id}/delete")
    public String deleteJobPostingByAdmin(@PathVariable("id") Integer id) {
    	
    	// Fetch the job posting (to ensure it exists)
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting == null) {
       	 logger.warn("User not suitable for deleting jobposting");
        	return "login";
        }
        jobPostingService.deleteJobPosting(jobPosting);
        return "redirect:/view/jobposts";
    }
    
    //deleting a jobPosting By
    //employerid and jobId
    //by an employer only
    @PostMapping("/{jobId}/delete/byemployer")
    public String deleteJobPostingByEmployerId(@PathVariable("jobId") Integer jobId,
    		@ModelAttribute("employer") Employer employer) {
    	    	
       if(employer == null) {
          	 logger.warn("User not suitable for deleting jobposting");
        	return "login";
        }
        
        // Fetch the logged-in employerId and job posting by jobId 
        JobPosting jobPosting = jobPostingService.getJobPostingByEmployerIdAndJobId(employer.getEmployerId(), jobId);
        if (jobPosting == null) {
         	 logger.warn("Id didnot match whether the employer or jobpost");
        	return "login";
        }
        
        // Otherwise, proceed with deletion
        jobPostingService.deleteJobPosting(jobPosting);
        return "redirect:/employers/profile";
    	
    }
    
   
    //edit job posts 
	@GetMapping("/{jobId}/edit/by/{employerId}")
	public String getEditForm(
			@PathVariable("jobId") Integer jobId,
			@PathVariable("employerId") Integer employerId,
			@ModelAttribute("employer") Employer employer,
			Model model) {
 		
    	//checking if Form username and jobid can edit the post
    	JobPosting jobPost = jobPostingService.getJobPostingById(jobId);
    	Employer submittedemployer = employerService.findByEmployerId(employer.getEmployerId()); 
     
       if( jobPost == null || submittedemployer == null || employer == null) {
       	 logger.warn("Employer or jobid cannot access to edit jobPost");
    	   return"login";
       }
       if (!submittedemployer.equals(employer)) {
    	   logger.warn("Submitted and loggedin employer doesnot match");
    	   return"login";
       }
		model.addAttribute("employer", submittedemployer);  
		model.addAttribute("jobPosting", jobPost);
		
	    return "jobpostform";  
	}
	
	
	@PostMapping("/{jobId}/edit/by/{employerId}")
	public String postEditForm(@PathVariable("jobId") Integer jobId,
			@ModelAttribute("jobPosting") JobPosting updatedJobPosting,
			@ModelAttribute("employer") Employer employer,
			Model model) {
	    
	    JobPosting existingJobPosting = jobPostingService.getJobPostingById(jobId);
	    if (existingJobPosting == null) {
	    	logger.warn("Job post not found for jobId: \" + jobId");
	        return "redirect:/view/jobposts";  
	    }

	    List<JobPosting> loggedInEmployerPosts = jobPostingService.findByEmployerCompanyName(employer.getCompanyName());
	    boolean canEdit = loggedInEmployerPosts.stream().anyMatch(post -> post.getJobId() == jobId);

	    if (!canEdit) {
	    	logger.warn("The logged-in user does not have permission to edit job post with ID: \" + jobId");
	        return "login";  
	    }

	    // Copy updated values from the form (updatedJobPosting) to existingJobPosting
	    existingJobPosting.setTitle(updatedJobPosting.getTitle());
	    existingJobPosting.setJobDescription(updatedJobPosting.getJobDescription());
	    existingJobPosting.setCityLocation(updatedJobPosting.getCityLocation());
	    existingJobPosting.setJobType(updatedJobPosting.getJobType());
	    existingJobPosting.setSalaryRange(updatedJobPosting.getSalaryRange());
	    existingJobPosting.setJobCategory(updatedJobPosting.getJobCategory());
	    existingJobPosting.setRequirements(updatedJobPosting.getRequirements());
	    existingJobPosting.setApplicationDeadline(updatedJobPosting.getApplicationDeadline());
	    existingJobPosting.setExperienceLevel(updatedJobPosting.getExperienceLevel());
	    existingJobPosting.setRemote(updatedJobPosting.isRemote());
	    existingJobPosting.setContactEmail(updatedJobPosting.getContactEmail());

	    // Save the updated job post
	    jobPostingService.updateJobPosting(jobId, existingJobPosting);

	    return "redirect:/employers/profile";
	}	
	
	//deleting a jobPosting By admin 
    //using employerid and jobId
	//send notification message to employer 
    @PostMapping("/{jobId}/delete/byadmin/of/employerId/{employerId}")
    public String deleteJobPostingByAdmin(@PathVariable("jobId") Integer jobId,
    		@PathVariable("employerId") Integer employerId,
    		 @RequestParam("message") String message) {
    	    	
        String email = UserAuthorization.getLoggedInUserEmail();
        if (email != null) {
            Users user = usersService.findByEmail(email);
            if (user != null && Usertype.ADMIN.equals(user.getUsertype())) {
                JobPosting jobPost = jobPostingService.getJobPostingByEmployerIdAndJobId(employerId, jobId);
                Users employer = usersService.findByEmployer_employerId(employerId);
                if (jobPost == null || employer == null) {
                	 logger.warn("Id didnot match whether the employer or jobpost");
               	return "login";
               }
                String deleteMessage = "Your jobPost "+ jobPost.getTitle() +" has been deleted by admin " +message;
                notificationService.createNotification(employer, deleteMessage);
                jobPostingService.deleteJobPosting(jobPost);
            }
        }
		  return "redirect:/admin/view/dashboard";    	
    }
    
}
