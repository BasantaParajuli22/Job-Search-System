package com.example.springTrain.controller;

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
import com.example.springTrain.entity.Admin;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.AdminService;
import com.example.springTrain.service.EmployerService;
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
	private AdminService adminService;
	@Autowired
	private UsersService usersService;
	

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
    
	//to visit jobseekers profile
    @GetMapping("/employers/profile")
    public String getEmployersProfile(Model model) {
        return "employer-profile";
    }
    
    //verifying submitted and loggedin jobSeeker are same
    //to edit profile
    @GetMapping("/jobseekers/profile/edit/{jobSeekerUsername}")
    public String getJobseekerProfileEdit(Model model,
    		@PathVariable("jobSeekerUsername") String jobSeekerUsername) {
        
		String loggedinUsername = UserAuthorization.getLoggedInJobSeekerUsername();

       JobSeeker submittedjobSeeker = jobSeekerService.findByUsername(jobSeekerUsername);
       if( submittedjobSeeker == null) {
    	   logger.warn("Submitted jobSeeker not found"); 
    	   return"login";
       }
       
       String submittedUsername = submittedjobSeeker.getJobSeekerUsername();
       if(!loggedinUsername.equals(submittedUsername)) {
    	   logger.warn("not same submittedjobSeeker and loggedinJobSeeker"); 
    	   return"login";
       }

      // model.addAttribute("jobSeeker", submittedjobSeeker);//adding object  
       //here setting values in jobSeekerDTO
       JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
       jobSeekerDTO.setJobSeekerUsername(submittedjobSeeker.getJobSeekerUsername());//setting username to send username for post method 
       jobSeekerDTO.setEmail(submittedjobSeeker.getEmail());
       jobSeekerDTO.setNumber(submittedjobSeeker.getNumber());
       jobSeekerDTO.setAddress(submittedjobSeeker.getAddress());
       jobSeekerDTO.setSkills(submittedjobSeeker.getSkills());
       
       model.addAttribute("jobSeekerDTO",jobSeekerDTO);
       //this will be false during registration but true in edit
       model.addAttribute("isEditMode",true);

       //here submitted jobSeeker will not be same as new one
        return "jobseeker-form";
    }
    
    @PostMapping("/jobseekers/profile/edit/{jobSeekerUsername}")
    public String postJobseekerProfileEdit(Model model,
    		@ModelAttribute JobSeekerDTO jobSeekerDTO,
    		@PathVariable("jobSeekerUsername") String jobSeekerUsername) {

		String loggedinUsername = UserAuthorization.getLoggedInJobSeekerUsername();

		//there is two same username in user and jobseeker entity
       JobSeeker submittedjobSeeker = jobSeekerService.findByUsername(jobSeekerUsername);
       Users submittedUser = usersService.findByUsername(jobSeekerUsername);
       if( submittedjobSeeker == null || submittedUser == null) {
    	   logger.warn("Submitted jobSeeker not found"); 
    	   return"login";
       }
       
       String submittedUsername = submittedjobSeeker.getJobSeekerUsername();
       if(!loggedinUsername.equals(submittedUsername)) {
    	   logger.warn("not same submittedjobSeeker and loggedinJobSeeker"); 
    	   return"login";
       }
       
      //setting values directly to object
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
	@GetMapping("/employers/profile/edit/{CompanyName}")
	public String getEmployerProfile(Model model,
			@PathVariable("CompanyName") String CompanyName) {
		
		// Get the currently logged-in user's username
		String loggedinUsername = UserAuthorization.getLoggedInEmployerUsername();
		
		//there is two same username in user and jobseeker entity
       Employer submittedEmployer = employerService.findByCompanyName(CompanyName);
       Users submittedUser = usersService.findByUsername(CompanyName);
       if( submittedEmployer == null || submittedUser == null) {
    	   logger.warn("Submitted company not found in get"); 
    	   return"login";
       }

       String submittedUsername = submittedEmployer.getCompanyName();
       if(!loggedinUsername.equals(submittedUsername)) {
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
       model.addAttribute("isEditMode",true);
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
	
	//to visit admin profile
	@GetMapping("/admin/profile")
	public String getAdminProfile(Model model) {
		
		// Get the currently logged-in user's username
		String loggedinusername = UserAuthorization.getLoggedInUsername();
		Admin admin = adminService.findByUsername(loggedinusername);
		if (loggedinusername == null || admin == null) {
			return "login";
		}
		
		model.addAttribute("admin", admin);  // Add the admin to the model
		return "admin-profile";
		
	}
}