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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springTrain.dto.ProfileDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.UsersService;
import com.example.springTrain.validation.ValidationError;


@Controller
public class ProfileController{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private JobApplicationService jobApplicationService;
	
    
	//to visit jobseekers profile
    @GetMapping("/jobseekers/profile")
    public String getJobseekersProfile(Model model,
    		@ModelAttribute ("jobSeeker") JobSeeker jobSeeker) {
    	
    	model.addAttribute("loggedInJobSeeker",jobSeeker);
        return "jobseeker/jobseeker-profile";
    }
    
	//to visit employers profile
    //same employers login required
    @GetMapping("/employers/profile")
    public String getEmployersProfile(Model model,
    		@ModelAttribute("employer")Employer employer) {
    	
    	List<JobPosting> myJobPosts = jobPostingService.getJobPostingByEmployerId(employer.getEmployerId());		
		List<Integer> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(employer.getEmployerId());
		
		model.addAttribute("myJobPosts", myJobPosts);
		model.addAttribute("jobAppCount",jobAppCount);	
		model.addAttribute("loggedInEmployer",employer);
		
        return "employer/employer-profile";
    }
    
    @GetMapping("/jobseekers/profile/edit/{jobSeekerId}")
    public String getJobseekerProfileEdit(Model model,
    		@PathVariable("jobSeekerId") Integer jobSeekerId,
    		@ModelAttribute("jobSeeker")JobSeeker jobSeeker) {
        
       JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);      
       if( submittedJobSeeker == null || jobSeeker == null) {
    	   logger.warn("Submitted jobSeeker not found"); 
    	   return"login";
       }
       if(!submittedJobSeeker.getJobSeekerId().equals(jobSeeker.getJobSeekerId())) {
    	   logger.warn("not same submittedjobSeeker and loggedinJobSeeker"); 
    	   return"login";
       }
       ProfileDTO profileDTO = new ProfileDTO();
       profileDTO.setUserId(submittedJobSeeker.getJobSeekerId());
       profileDTO.setFullName(submittedJobSeeker.getFullName());
       profileDTO.setSkills(submittedJobSeeker.getSkills());
       profileDTO.setNumber(submittedJobSeeker.getNumber());

       model.addAttribute("profileDTO", profileDTO);
       model.addAttribute("userType", "jobseeker");
       
       return "jobseeker/profile-edit";

    }
    
    //logIn required
	@GetMapping("/employers/profile/edit/{employerId}")
	public String getEmployerProfile(Model model,
			@PathVariable("employerId") Integer employerId,
			@ModelAttribute ("employer")Employer employer) {
		

		//there is two same username in user and jobseeker entity
       Employer submittedEmployer = employerService.findByEmployerId(employer.getEmployerId());
       Users submittedUser = usersService.findByEmployer_employerId(employer.getEmployerId());
       if(employer == null || submittedUser == null || submittedEmployer == null) {
    	   logger.warn("Submitted company not found to edit profile"); 
    	   return"login";
       }
       if(!employer.getEmployerId().equals(submittedUser.getEmployer().getEmployerId())) {
    	   logger.warn("LoggedIn user doesnot match submitted user"); 
    	   return"login";
       }
       //here setting values in jobSeekerDTO
       ProfileDTO profileDTO = new ProfileDTO();
       profileDTO.setUserId(submittedEmployer.getEmployerId());
       profileDTO.setCompanyName(submittedEmployer.getCompanyName());
       profileDTO.setCompanyDescription(submittedEmployer.getCompanyDescription());
       profileDTO.setAddress(submittedEmployer.getAddress());
       profileDTO.setNumber(submittedEmployer.getNumber());

       model.addAttribute("profileDTO", profileDTO);

       return "employer/profile-edit";
	}
	
	
	@PostMapping("/jobseekers/profile/edit/{jobSeekerId}")
    public String postJobseekerProfileEdit(Model model,
    		@ModelAttribute ProfileDTO jobSeekerDTO,
    		@PathVariable("jobSeekerId") Integer jobSeekerId,
    		@ModelAttribute("jobSeeker")JobSeeker jobSeeker) {

	   JobSeeker submittedjobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);      
	   Users submittedUser = usersService.findByJobSeeker_jobSeekerId(jobSeekerId);
	   if( submittedjobSeeker == null || submittedUser == null) {
	    	logger.warn("Submitted jobSeeker not found"); 
	    	return"login";
	   } 
       if(!jobSeeker.getJobSeekerId().equals(submittedUser.getJobSeeker().getJobSeekerId())) {
    	   logger.warn("Submitted jobSeekerId not found in JobSeeker and Users table"); 
    	   return"login";
       }       

       jobSeekerService.updateJobSeeker(jobSeekerDTO,submittedjobSeeker);      

        return "redirect:/jobseekers/profile";
    }
    
    //verifying submitted and loggedin jobSeeker are same
    //to edit profile
	//need employer login
    @PostMapping("/employers/profile/edit/{employerId}")
    public String getEmployerProfileEdit(Model model,
    		@ModelAttribute ProfileDTO employerDTO,
    		@PathVariable("employerId") Integer employerId,
    		@ModelAttribute ("employer")Employer employer,
    		RedirectAttributes redirectAttributes) {
        
	  	ValidationError validationError = new ValidationError();
	  	validationError.clear();
	  	
	  	Employer existinguser = employerService.findByCompanyName(employerDTO.getCompanyName());
	  	//error if companyName exists already and
	  	//if it doesnot have same employerId as being edited employerId
	  	if(existinguser != null && !existinguser.getEmployerId().equals(employerId)) {
	  		validationError.setUsername("Sorry username is already taken ");
	  	}
	  	
		//chekcing if id exists in both table 
       Employer submittedEmployer = employerService.findByEmployerId(employerId);
       Users submittedUser = usersService.findByEmployer_EmployerId(employerId);
       if( submittedEmployer == null || submittedUser == null) {
    	   logger.warn("Submitted company not found in table"); 
    	   return"login";
       }
       
	  	if(validationError.hasErrors()) {
	  		// Re-add the input data to the model to repopulate the form
	  		System.out.println(validationError);
	  		model.addAttribute("employerDTO", employerDTO);
	  		redirectAttributes.addFlashAttribute("errorMessage",validationError.getUsername());
	  		return "redirect:/employers/profile/edit/" + employerId;
	  	}
	  	
	  	employerService.updateEmployer(employerDTO,submittedEmployer);      

        return "redirect:/employers/profile";
    }
	
  
}