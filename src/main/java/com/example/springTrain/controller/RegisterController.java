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
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.UsersService;
import com.example.springTrain.validation.ValidationError;

@Controller
public class RegisterController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	private UsersService usersService;

	@Autowired
	private EmployerService employerService;

	@Autowired
	private JobSeekerService jobSeekerService;

	@GetMapping("/home")
	public String getHomepage() {
		return "home";
	}

	
	//Login page (Get method only)
	//rest is handled by Spring security
	@GetMapping("/login")
	public String getLogin() {
	    return "login"; // Redirect back to login
	}
	@GetMapping("/logout")
    public String logout() {
        // Any custom logic before redirecting to the login page (optional)
        return "redirect:/login?logout"; 
    }
	// to register as employers
	  @GetMapping("/employer/register")
	  public String getemployform(Model model) {
		    model.addAttribute("employerDTO", new EmployerDTO()); // Ensure this matches the th:object in your form
		    return "employer/employer-form";
	  }
	// to register as jobseekers
	  @GetMapping("/jobseeker/register")
	  public String getemployeeform(Model model) {
		    model.addAttribute("jobSeekerDTO", new JobSeekerDTO()); // Ensure this matches the th:object in your form
		    return "jobseeker/jobseeker-form";
	  }

	  @PostMapping("/jobseeker/register")
	  public String registerJobSeeker(@ModelAttribute("jobSeekerDTO") JobSeekerDTO jobSeekerDTO, Model model) {
	      ValidationError validationError = new ValidationError();
	      validationError.clear();

	      // Check if email is unique in both JobSeeker and Employer tables
	      JobSeeker existingJobEmail = jobSeekerService.findByEmail(jobSeekerDTO.getEmail());
	      Employer existingEmployerEmail = employerService.findByEmail(jobSeekerDTO.getEmail());
	      if (existingEmployerEmail != null || existingJobEmail != null) {
	          validationError.setEmail("Sorry, this email is already taken.");
	      }

	      // Validate password confirmation
	      if (!jobSeekerDTO.getPassword().equals(jobSeekerDTO.getConfirmPassword())) {
	          validationError.setPassword("Passwords do not match.");
	      }

	      if (validationError.hasErrors()) {
	          // Re-add input data and errors to the model
	          model.addAttribute("jobSeekerDTO", jobSeekerDTO);
	          model.addAttribute("error", validationError);
	          return "jobseeker/jobseeker-form";
	      }

	      // Save the JobSeeker
	      usersService.createJobSeeker(jobSeekerDTO);
	      return "redirect:/login";
	  }


	  	@PostMapping("/employer/register")
	  	public String registerEmployer(@ModelAttribute EmployerDTO employerDTO, Model model) {
	  		
	  	ValidationError validationError = new ValidationError();
	  	validationError.clear();
	  	
	  	//companyName should be unique in employer table
	  	Employer existinguser = employerService.findByCompanyName(employerDTO.getCompanyName());
	  	if(existinguser != null) {
	  		validationError.setUsername("Sorry username is already taken ");
	  		System.out.println("Sorry username is already taken " + employerDTO.getCompanyName()); // Debugging	
	  	}
	  	//email should be unique in employer table
	  	JobSeeker existingJobEmail = jobSeekerService.findByEmail(employerDTO.getEmail());
	  	Employer existingEmployerEmail = employerService.findByEmail(employerDTO.getEmail());
	  	if(existingEmployerEmail != null || existingJobEmail != null) {
	  		validationError.setEmail("Sorry email is already taken ");
	  	}		
	  	
	  	if(validationError.hasErrors()) {
	  		// Re-add the input data to the model to repopulate the form
	  		model.addAttribute("employerDTO", employerDTO);
	  		model.addAttribute("error", validationError);
	  		return "employer/employer-form";
	  	}else {
	  		usersService.createEmployer(employerDTO);
	  	}
	  	return "redirect:/login";
	  }
	  	
	  	
	  
	  @PostMapping("/admin/block-user/{userId}")
	  public String blockUserByAdmin(@PathVariable("userId") Integer userId) {
		  Users user = usersService.findByUserId(userId);
		  if(user !=  null) {
			  usersService.blockUser(user);
		  }
		  return "redirect:/admin/view/dashboard";
	  }
	  
	  @PostMapping("/admin/un-block-user/{userId}")
	  public String unBlockUserByAdmin(@PathVariable("userId") Integer userId) {
		  Users user = usersService.findByUserId(userId);
		  if(user !=  null) {
			  usersService.unBlockUser(user);
		  }
		  return "redirect:/admin/view/dashboard";
	  }
	  
	  @GetMapping("/access-denied")
	    public String accessDenied() {
	        return "access-denied"; 
	    }
}
