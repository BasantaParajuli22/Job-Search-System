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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String getJobPostForm(Model model) {
	     	 
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");
		Employer loggedInEmployer = employerService.findByEmployerId(loggedInEmployerId);

         if(loggedInEmployer == null) {
        	 logger.warn("User not available to create jobPosts");
            return "login";
         }
        model.addAttribute("employer", loggedInEmployer);
        model.addAttribute("jobPosting", new JobPosting());  // Pass a new JobPosting object
        return "jobpostform";
	}
	
	//for posting jobpost employerId is needed
	//employerId is set in joposting 
	//and jobposting is created
	@PostMapping("/new/create")
	public String createJobPostForm(
			@ModelAttribute JobPosting jobPosting,
			@RequestParam("employerId") Long employerId,Model model,
			RedirectAttributes redirectAttributes) {
		
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");
		Employer loggedInEmployer = employerService.findByEmployerId(loggedInEmployerId);

		jobPostingService.createJobPosting(jobPosting,loggedInEmployer);//jobposting created
		
        redirectAttributes.addFlashAttribute("successMessage", "Your Job Post has been saved successfully!");
		return "redirect:/employers/profile";
	}
	
	
	//DELETE 
	//by ALL
	//all jobposting can be deleted by all
    @PostMapping("/{id}/delete")
    public String deleteJobPostingByAdmin(@PathVariable("id") Long id) {
    	
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
    public String deleteJobPostingByEmployerId(@PathVariable("jobId") Long jobId,
    		Model model,RedirectAttributes redirectAttributes
    		) {
    	    	
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");
        // Fetch the logged-in employerId and job posting by jobId 
        JobPosting jobPosting = jobPostingService.getJobPostingByEmployerIdAndJobId(loggedInEmployerId, jobId);
        if (jobPosting == null) {
         	 logger.warn("Id didnot match whether the employer or jobpost");
        	return "login";
        }
        
        // Otherwise, proceed with deletion
        jobPostingService.deleteJobPosting(jobPosting);
        redirectAttributes.addFlashAttribute("successMessage", "Your Job Post has been deleted successfully!");

        return "redirect:/employers/profile";
    	
    }
    
   
    //edit job posts 
	@GetMapping("/{jobId}/edit/by/{employerId}")
	public String getEditForm(
			@PathVariable("jobId") Long jobId,
			@PathVariable("employerId") Long employerId,
			Model model) {
 		
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");

    	//checking if Form username and jobid can edit the post
    	JobPosting jobPost = jobPostingService.getJobPostingById(jobId);
    	Employer submittedemployer = employerService.findByEmployerId(employerId); 
    	Employer loggedInEmployer = employerService.findByEmployerId(loggedInEmployerId); 

       if( jobPost == null || submittedemployer == null) {
       	 logger.warn("JobPost or submitted employer are null to edit post");
    	   return"login";
       }
       if(!submittedemployer.equals(loggedInEmployer)) {
         	 logger.warn("submitted employer doesnot match to edit post");
      	   return"login";
         }
       
		model.addAttribute("employer", submittedemployer);  
		model.addAttribute("jobPosting", jobPost);
		
	    return "jobpostform";  
	}
	
	
	@PostMapping("/{jobId}/edit/by/{employerId}")
	public String postEditForm(@PathVariable("jobId") Long jobId,
			@ModelAttribute("jobPosting") JobPosting updatedJobPosting,
			Model model,RedirectAttributes redirectAttributes) {
		
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");

	    JobPosting existingJobPosting = jobPostingService.getJobPostingById(jobId);
	    if (existingJobPosting == null) {
	    	logger.warn("Job post not found for jobId: \" + jobId");
	        return "redirect:/view/jobposts";  
	    }

	    List<JobPosting> loggedInEmployerPosts = jobPostingService.findByEmployerId(loggedInEmployerId);
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

	    // Save the updated job post
	    jobPostingService.updateJobPosting(jobId, existingJobPosting);

        redirectAttributes.addFlashAttribute("successMessage", "Your Job Post has been edited successfully!");
	    return "redirect:/employers/profile";
	}	
	
	//deleting a jobPosting By admin 
    //using employerid and jobId
	//send notification message to employer 
    @PostMapping("/{jobId}/delete/byadmin/of/employerId/{employerId}")
    public String deleteJobPostingByAdmin(@PathVariable("jobId") Long jobId,
    		@PathVariable("employerId") Long employerId,
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
