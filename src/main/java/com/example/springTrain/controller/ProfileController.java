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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springTrain.dto.ProfileDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.FileStorageService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.validation.ValidationError;


@Controller
public class ProfileController{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private FileStorageService  fileStorageService;
	
	
    
	//to visit jobseekers profile
	//login required
    @GetMapping("/jobseekers/profile")
    public String getJobseekersProfile(Model model) {
    	
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");
        JobSeeker loogedInJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);      
		
        model.addAttribute("jobSeeker",loogedInJobSeeker);	
    	model.addAttribute("loggedInJobSeeker",loogedInJobSeeker);
        return "jobseeker/jobseeker-profile";
    }
    
	//to visit employers profile
    //same employers login required
    @GetMapping("/employers/profile")
    public String getEmployersProfile(Model model) {
    	
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");

    	List<JobPosting> myJobPosts = jobPostingService.getJobPostingByEmployerId(loggedInEmployerId);		
		List<Long> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(loggedInEmployerId);
	    Employer loggedInEmployer = employerService.findByEmployerId(loggedInEmployerId);

		model.addAttribute("myJobPosts", myJobPosts);
		model.addAttribute("jobAppCount",jobAppCount);	
		model.addAttribute("loggedInEmployer",loggedInEmployer);
		model.addAttribute("employer",loggedInEmployer);

        return "employer/employer-profile";
    }
    
    @GetMapping("/jobseekers/profile/edit/{jobSeekerId}")
    public String getJobseekerProfileEdit(Model model,
    		@PathVariable("jobSeekerId") Long jobSeekerId) {
        
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");
		JobSeeker loogedInJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
		
		JobSeeker submittedJobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId); 

		if( submittedJobSeeker == null || loogedInJobSeeker == null) {
			logger.warn("Submitted jobSeeker not found"); 
			return"login";
		}
       if(!submittedJobSeeker.getJobSeekerId().equals(loggedInjobSeekerId)) {
    	   logger.warn("not same submittedjobSeeker and loggedinJobSeeker"); 
    	   return"login";
       }
       ProfileDTO profileDTO = new ProfileDTO();
       profileDTO.setUserId(submittedJobSeeker.getJobSeekerId());
       profileDTO.setFullName(submittedJobSeeker.getFullName());
       profileDTO.setSkills(submittedJobSeeker.getSkills());
       profileDTO.setNumber(submittedJobSeeker.getNumber());
       profileDTO.setDescription(submittedJobSeeker.getDescription());

       model.addAttribute("profileDTO", profileDTO);
       model.addAttribute("userType", "jobseeker");
       
       return "jobseeker/profile-edit";

    }
    
    @PostMapping("/jobseekers/profile/edit/{jobSeekerId}")
    public String postJobseekerProfileEdit(Model model,
    		@ModelAttribute ProfileDTO jobSeekerDTO,
    		@PathVariable("jobSeekerId") Long jobSeekerId,
    		RedirectAttributes redirectAttributes) {

		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");
	       
		JobSeeker submittedjobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);      
		JobSeeker loggedInJobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);      
	   
	   if( submittedjobSeeker == null || loggedInJobSeeker == null) {
	    	logger.warn("Submitted jobSeeker not found"); 
	    	return"login";
	   } 
       if(!loggedInjobSeekerId.equals(jobSeekerId)) {
    	   logger.warn("Submitted jobSeekerId not found in JobSeeker and Users table"); 
    	   return"login";
       }       

       jobSeekerService.updateJobSeeker(jobSeekerDTO,submittedjobSeeker);      
       redirectAttributes.addFlashAttribute("successMessage", "Your Profile has been edited successfully!");

        return "redirect:/jobseekers/profile";
    }
    
    //for changing profile picture
    @PostMapping("/jobseekers/profile/edit/{jobSeekerId}/profile-picture")
    public String postJobSeekerProfileImageChange(Model model,
    		 @PathVariable("jobSeekerId")Long jobSeekerId,
    		@RequestParam("image")MultipartFile imageFile,
    		RedirectAttributes redirectAttributes) {
    	
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");
		JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
		
		 if(!loggedInjobSeekerId.equals(jobSeekerId)) {
	    	   logger.warn("Submitted jobSeekerId not matched in JobSeeker"); 
	    	   return"login";
	       }  
    	Boolean validImageType = fileStorageService.checkImageType(imageFile);
   	    if(!validImageType) {
   	        redirectAttributes.addFlashAttribute("errorMessage", "Image File type needs to be in .png .jpg .jpeg .webp format");
   	    }
        jobSeekerService.updateJobSeekerProfilePicture(jobSeeker,imageFile);      
        redirectAttributes.addFlashAttribute("successMessage", "Your Profile Picture has been edited successfully!");

       	return "redirect:/jobseekers/profile";
    }
    
    //for changing profile resume
    @PostMapping("/jobseekers/profile/edit/{jobSeekerId}/profile-resume")
    public String postJobSeekerProfileResumeChange(Model model,
    		 @PathVariable("jobSeekerId")Long jobSeekerId,
    		@RequestParam("resume")MultipartFile file,
    		RedirectAttributes redirectAttributes) {
    	
		Long loggedInjobSeekerId = (Long) model.getAttribute("jobSeekerId");
		JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(loggedInjobSeekerId);
		
		 if(!loggedInjobSeekerId.equals(jobSeekerId)) {
	    	   logger.warn("Submitted jobSeekerId not matched in JobSeeker"); 
	    	   return"login";
	       }  
    	Boolean validImageType = fileStorageService.checkFileType(file);
   	    if(!validImageType) {
   	        redirectAttributes.addFlashAttribute("errorMessage", "File type needs to be in .pdf format");
   	    }
        jobSeekerService.updateJobSeekerProfileResume(jobSeeker,file);      
        redirectAttributes.addFlashAttribute("successMessage", "Your Resume has been edited successfully!");

       	return "redirect:/jobseekers/profile";
    }
    
    
    //logIn required
	@GetMapping("/employers/profile/edit/{employerId}")
	public String getEmployerProfile(Model model,
			@PathVariable("employerId") Long employerId) {
		
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");

		//there is two same username in user and jobseeker entity
       Employer submittedEmployer = employerService.findByEmployerId(employerId);
       if( submittedEmployer == null || loggedInEmployerId == null) {
    	   logger.warn("Submitted company not found to edit profile"); 
    	   return"login";
       }
       if(!loggedInEmployerId.equals(employerId)) {
    	   logger.warn("LoggedIn user doesnot match submitted user"); 
    	   return"login";
       }
       //here setting values in jobSeekerDTO
       ProfileDTO profileDTO = new ProfileDTO();
       profileDTO.setUserId(submittedEmployer.getEmployerId());
       profileDTO.setCompanyName(submittedEmployer.getCompanyName());
       profileDTO.setDescription(submittedEmployer.getCompanyDescription());
       profileDTO.setAddress(submittedEmployer.getAddress());
       profileDTO.setNumber(submittedEmployer.getNumber());
       profileDTO.setWebsite(submittedEmployer.getWebsite());    

       model.addAttribute("profileDTO", profileDTO);

       return "employer/profile-edit";
	}
	
	
	
    //verifying submitted and loggedin jobSeeker are same
    //to edit profile
	//need employer login
    @PostMapping("/employers/profile/edit/{employerId}")
    public String getEmployerProfileEdit(Model model,
    		@ModelAttribute ProfileDTO employerDTO,
    		@PathVariable("employerId") Long employerId,	
    		RedirectAttributes redirectAttributes) {
    	  	
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");

	  	ValidationError validationError = new ValidationError();
	  	validationError.clear();
	  	
	  	Employer existinguser = employerService.findByCompanyName(employerDTO.getCompanyName().toLowerCase());
	  	//error if companyName exists already and
	  	//if it doesnot have same employerId as being edited employerId
	  	if(existinguser != null && !existinguser.getEmployerId().equals(employerId)) {
	  		validationError.setCompanyName("Sorry username is already taken ");
	  		
	  	}
	  	
		//chekcing if id exists in both table 
       Employer submittedEmployer = employerService.findByEmployerId(employerId);
       if( submittedEmployer == null || employerId != loggedInEmployerId ) {
    	   logger.warn("Submitted company not found or matched in table"); 
    	   return"login";
       }
       
	  	if(validationError.hasErrors()) {
	  		// Re-add the input data to the model to repopulate the form
	  		System.out.println(validationError);
	  		model.addAttribute("employerDTO", employerDTO);
	  		redirectAttributes.addFlashAttribute("errorMessage",validationError.getCompanyName());
	  		return "redirect:/employers/profile/edit/" + employerId;
	  	}
	  	
	  	employerService.updateEmployer(employerDTO,submittedEmployer);      
        redirectAttributes.addFlashAttribute("successMessage", "Your Company's Profile has been edited successfully!");

        return "redirect:/employers/profile";
    }
	
    @PostMapping("/employers/profile/edit/{employerId}/company-logo")
    public String getEmployerProfileImageChange(Model model,
		 @PathVariable("employerId")Long employerId,
		 @RequestParam("image")MultipartFile companyLogo,
 		RedirectAttributes redirectAttributes) {
    	
    	Long loggedInEmployerId = (Long) model.getAttribute("employerId");
		Employer employer = employerService.findByEmployerId(employerId);

        if(!loggedInEmployerId.equals(employerId)) {
	    	   logger.warn("Submitted EmployerId not matched"); 
	    	   return"login";
	       }
        
    	Boolean validImageType = fileStorageService.checkImageType(companyLogo);
   	    if(!validImageType) {
   	        redirectAttributes.addFlashAttribute("errorMessage", "Image File type needs to be in .png .jpg .jpeg .webp format");
   	    }
   		employerService.updateEmployerProfilePicture(employer,companyLogo);   
        redirectAttributes.addFlashAttribute("successMessage", "Your Company Logo has been edited successfully!");

    	return "redirect:/employers/profile";
    }
}