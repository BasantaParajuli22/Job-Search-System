package com.example.springTrain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springTrain.dto.EmployerDTO;
import com.example.springTrain.dto.JobSeekerDTO;
import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.Users;
import com.example.springTrain.repository.UsersRepository;
import com.example.springTrain.service.UsersService;
import com.example.springTrain.validation.ValidationError;

@Controller
public class RegisterController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersService usersService;

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
		    return "employer-form";
	  }
	// to register as jobseekers
	  @GetMapping("/jobseeker/register")
	  public String getemployeeform(Model model) {
		    model.addAttribute("jobSeekerDTO", new JobSeekerDTO()); // Ensure this matches the th:object in your form
		    return "jobseeker-form";
	  }

	  @PostMapping("/jobseeker/register")
	  public String registerJobSeeker(@ModelAttribute JobSeekerDTO jobSeekerDTO, Model model) {
	  		
	  	ValidationError validationError = new ValidationError();
	  	validationError.clear();
	  		
	  	//username should be unique in user table
	  	Users existinguser = usersRepository.findByUsername(jobSeekerDTO.getJobSeekerUsername());
	  	if(existinguser != null) {
	  		validationError.setUsername("Sorry username is already taken ");
	  	}
	  	//email should be unique in job_seeker table
	  	Users existingjobemail = usersRepository.findByEmail(jobSeekerDTO.getEmail());
	  	if(existingjobemail != null) {
	  		validationError.setEmail("Sorry email is already taken ");
	  	}	
	  		
	  	if(validationError.hasErrors()) {
	  	// Re-add the input data to the model to repopulate the form
	  		model.addAttribute("jobSeekerDTO", jobSeekerDTO);
	  		model.addAttribute("error", validationError);//sending both errors in 1 error variable
	  		return "jobseeker-form";
	  	}else {
	  		
	  		// Create User entity
	  		//getting data from DTO and setting entities and saving
	  		Users user = new Users();
	  		user.setUsername(jobSeekerDTO.getJobSeekerUsername());
	  		user.setPassword(jobSeekerDTO.getPassword());
	  		user.setEmail(jobSeekerDTO.getEmail());
	  		user.setUsertype(jobSeekerDTO.getUsertype());

	  		// Create JobSeeker entity
	  		JobSeeker jobSeeker = new JobSeeker();
	  		jobSeeker.setJobSeekerUsername(jobSeekerDTO.getJobSeekerUsername());
	  		jobSeeker.setEmail(jobSeekerDTO.getEmail());
	  		jobSeeker.setNumber(jobSeekerDTO.getNumber());
	  		jobSeeker.setAddress(jobSeekerDTO.getAddress());
	  		jobSeeker.setSkills(jobSeekerDTO.getSkills());
	  		//jobSeeker.setResume(jobSeekerDTO.getResume());

	  		// Call the service method to save the user and job seeker
	  		usersService.createJobSeeker(user, jobSeeker);      
	  		return "login";
	  	}	

	  }

	  	@PostMapping("/employer/register")
	  	public String registerEmployer(@ModelAttribute EmployerDTO employerDTO, Model model) {
	  		
	  	ValidationError validationError = new ValidationError();
	  	validationError.clear();
	  	
	  	//username should be unique in user table
	  	Users existinguser = usersRepository.findByUsername(employerDTO.getCompanyName());
	  	if(existinguser != null) {
	  		validationError.setUsername("Sorry username is already taken ");
	  		System.out.println("Sorry username is already taken " + employerDTO.getCompanyName()); // Debugging	
	  	}
	  	//email should be unique in employer table
	  	Users existingjobemail = usersRepository.findByEmail(employerDTO.getEmail());
	  	if(existingjobemail != null) {
	  		validationError.setEmail("Sorry email is already taken ");
	  		System.out.println("Sorry email is already taken: " + employerDTO.getEmail()); // Debugging
	  	}	
	  	
	  	if(validationError.hasErrors()) {
	  		// Re-add the input data to the model to repopulate the form
	  		model.addAttribute("employerDTO", employerDTO);
	  		//sending both errors in 1 error variable
	  		model.addAttribute("error", validationError);
	  		return "employer-form";
	  	}else {
	  		Users user = new Users();
	  		user.setUsername(employerDTO.getCompanyName());
	  		user.setPassword(employerDTO.getPassword());
	  		user.setEmail(employerDTO.getEmail());
	  		user.setUsertype(employerDTO.getUsertype());

	  		Employer employer = new Employer();
	  		employer.setCompanyName(employerDTO.getCompanyName());
	  		employer.setCompanyDescription(employerDTO.getCompanyDescription());
	  		employer.setContactNumber(employerDTO.getContactNumber());
	  		employer.setAddress(employerDTO.getAddress());
	  		employer.setWebsite(employerDTO.getWebsite());
	  		employer.setEmail(employerDTO.getEmail());

	  		
	  		usersService.createEmployer(user, employer);

	  		return "login";
	  		}
	  	}
	  }
