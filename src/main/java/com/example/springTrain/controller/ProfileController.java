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

import com.example.springTrain.dto.EmployerDTO;
import com.example.springTrain.dto.JobSeekerDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.UsersService;


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
	

    @ModelAttribute
    public void addJobSeekerToModel(Model model) {
    	//getting LoggedInJobSeekerUsername in string
        String username = UserAuthorization.getLoggedInJobSeekerUsername();
        if (username != null) {//if found finding in jobSeeker entity
        	logger.warn(username);
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
        	logger.warn(username);
            Employer employer = employerService.findByCompanyName(username);
            if (employer != null) {//if found adding to model
                model.addAttribute("employer", employer);
            } 
        }
    }
    
	//to visit jobseekers profile
    @GetMapping("/jobseekers/profile")
    public String getJobseekersProfile(Model model) {
        return "jobseeker-profile";
    }
    
	//to visit employers profile and see their jobPosts
    //login as employer required
    @GetMapping("/employers/profile")
    public String getEmployersProfile(Model model,
    		@ModelAttribute("employer")Employer employer) {
    	
    	Employer loginemployer = employerService.findByEmployerId(employer.getEmployerId());
		
		if (loginemployer != null) {
	        List<JobPosting> myJobPosts = jobPostingService.getJobPostingByEmployerId(employer.getEmployerId());
	        model.addAttribute("myJobPosts", myJobPosts);
	    } else {
        	logger.warn("No companyName found for username: ");
	        return "login";
	    }
		
		//for loggin employer only
		//show how many applicants in all of employer jobPosts
		List<Integer> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(employer.getEmployerId());
		model.addAttribute("jobAppCount",jobAppCount);
		model.addAttribute("loginemployer",loginemployer);
		
        return "employer-profile";
    }
    
    //verifying submitted and loggedin jobSeeker are same
    //to edit profile
    //needs to be loggedin
    @GetMapping("/jobseekers/profile/edit/{jobSeekerId}")
    public String getJobseekerProfileEdit(Model model,
    		@PathVariable("jobSeekerId") Integer jobSeekerId,
    		@ModelAttribute("jobSeeker")JobSeeker jobSeeker) {
        
       JobSeeker submittedjobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);      
       if( submittedjobSeeker == null) {
    	   logger.warn("Submitted jobSeeker not found"); 
    	   return"login";
       }
       
       //loggedin and submiited jobSeeker should match
       if(!submittedjobSeeker.equals(jobSeeker)) {
    	   logger.warn("not same submittedjobSeeker and loggedinJobSeeker"); 
    	   return"login";
       }

      // model.addAttribute("jobSeeker", submittedjobSeeker);//adding object  
       //here setting values in jobSeekerDTO
       JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
       jobSeekerDTO.setJobSeekerId(submittedjobSeeker.getJobSeekerId());
       jobSeekerDTO.setJobSeekerUsername(submittedjobSeeker.getJobSeekerUsername());//
       jobSeekerDTO.setEmail(submittedjobSeeker.getEmail());
       jobSeekerDTO.setNumber(submittedjobSeeker.getNumber());
       jobSeekerDTO.setAddress(submittedjobSeeker.getAddress());
       jobSeekerDTO.setSkills(submittedjobSeeker.getSkills());
       
       model.addAttribute("jobSeekerDTO",jobSeekerDTO);
       //this will be false during registration but true in edit
       //model.addAttribute("isEditMode",true);

       //here submitted jobSeeker will not be same as new one
        return "jobseeker-form";
    }
    
    //jobSeekerID in user and jobSeeker table should be same always
    @PostMapping("/jobseekers/profile/edit/{jobSeekerId}")
    public String postJobseekerProfileEdit(Model model,
    		@ModelAttribute JobSeekerDTO jobSeekerDTO,
    		@PathVariable("jobSeekerId") Integer jobSeekerId) {

	   JobSeeker submittedjobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);      
	   Users submittedUser = usersService.findByJobSeeker_jobSeekerId(jobSeekerId);
	   if( submittedjobSeeker == null || submittedUser == null) {
	    	logger.warn("Submitted jobSeeker not found"); 
	    	return"login";
	   }
	      
       if( submittedjobSeeker.getJobSeekerId().equals(submittedUser.getJobSeeker().getJobSeekerId())) {
    	   logger.warn("Submitted jobSeekerId not found in JobSeeker and Users table"); 
    	   return"login";
       }

      //setting values directly to Users entity
       submittedUser.setEmail(jobSeekerDTO.getEmail());

      // submittedjobSeeker.setJobSeekerUsername(jobSeekerDTO.getJobSeekerUsername());//here user name not changing
       submittedjobSeeker.setEmail(jobSeekerDTO.getEmail());
       submittedjobSeeker.setNumber(jobSeekerDTO.getNumber());
       submittedjobSeeker.setAddress(jobSeekerDTO.getAddress());
       submittedjobSeeker.setSkills(jobSeekerDTO.getSkills());
       
       
       //update according to it
 		usersService.updateJobSeeker(submittedjobSeeker);      
 		usersService.updateUsers(submittedUser);         

        return "redirect:/jobseekers/profile";
    }
	
    
	//to visit employers profile
    //loggedin required
	@GetMapping("/employers/profile/edit/{employerId}")
	public String getEmployerProfile(Model model,
			@PathVariable("employerId") Integer employerId,
			@ModelAttribute ("employer")Employer employer) {
		
		
		//there is two same username in user and jobseeker entity
       Employer submittedEmployer = employerService.findByEmployerId(employer.getEmployerId());
       Users submittedUser = usersService.findByEmployer_employerId(employer.getEmployerId());
       if( submittedEmployer == null || submittedUser == null) {
    	   logger.warn("Submitted company not found in get"); 
    	   return"login";
       }

       if(!submittedEmployer.getEmployerId().equals(submittedUser.getEmployer().getEmployerId())) {
    	   logger.warn("not same submittedjobSeeker and loggedinJobSeeker"); 
    	   return"login";
       }
       
       //here setting values in jobSeekerDTO
       EmployerDTO employerDTO = new EmployerDTO();
       employerDTO.setCompanyName(submittedEmployer.getCompanyName());//setting companyName 
       employerDTO.setEmail(submittedEmployer.getEmail());
       employerDTO.setContactNumber(submittedEmployer.getContactNumber());
       employerDTO.setAddress(submittedEmployer.getAddress());
       employerDTO.setCompanyDescription(submittedEmployer.getCompanyDescription());
       employerDTO.setWebsite(submittedEmployer.getWebsite());
       
       model.addAttribute("employerDTO",employerDTO);
     //  model.addAttribute("isEditMode",true);
		return "employer-form";
	}
	
    //verifying submitted and loggedin jobSeeker are same
    //to edit profile
	//need employer login
    @PostMapping("/employers/profile/edit/{CompanyName}")
    public String getEmployerProfileEdit(Model model,
    		@ModelAttribute EmployerDTO employerDTO,
    		@PathVariable("CompanyName") String CompanyName) {
        
		String loggedinUsername = UserAuthorization.getLoggedInEmployerUsername();

		//there is two same username in user and jobseeker entity
       Employer submittedEmployer = employerService.findByCompanyName(CompanyName);
       Users submittedUser = usersService.findByUsername(CompanyName);
       
       if( submittedEmployer == null || submittedUser == null) {
    	   logger.warn("Submitted company not found in post"); 
    	   return"login";
       }
       
       String submittedUsername = submittedEmployer.getCompanyName();
       if(!loggedinUsername.equals(submittedUsername)) {
    	   logger.warn("not same submittedEmployer and loggedinEmployer"); 
    	   return"login";
       }
       
      //setting values directly to object
       submittedUser.setEmail(employerDTO.getEmail());

      
       submittedEmployer.setEmail(employerDTO.getEmail());
       submittedEmployer.setContactNumber(employerDTO.getContactNumber());
       submittedEmployer.setAddress(employerDTO.getAddress());
       submittedEmployer.setCompanyDescription(employerDTO.getCompanyDescription());
       submittedEmployer.setWebsite(employerDTO.getWebsite());

       
       //update according to it
 		usersService.updateEmployer(submittedEmployer);      
 		usersService.updateUsers(submittedUser);      

        return "redirect:/employers/profile";
    }
	
//	//to visit admin profile
//	@GetMapping("/admin/profile")
//	public String getAdminProfile(Model model) {
//		
//		// Get the currently logged-in user's username
//		String loggedinusername = UserAuthorization.getLoggedInUsername();
//		Admin admin = adminService.findByUsername(loggedinusername);
//		if (loggedinusername == null || admin == null) {
//			return "login";
//		}
//		
//		model.addAttribute("admin", admin);  // Add the admin to the model
//		return "admin-profile";
//		
//	}
//	
//	
}