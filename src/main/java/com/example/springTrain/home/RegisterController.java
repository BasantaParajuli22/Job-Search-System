package com.example.springTrain.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.service.UserService;
import com.example.dto.EmployerDTO;
import com.example.dto.JobSeekerDTO;
import com.example.dto.UserDTO;
import com.example.springTrain.user.Employer;
import com.example.springTrain.user.EmployerRepository;
import com.example.springTrain.user.JobSeeker;
//import com.example.springTrain.user.EmployerRepository;
import com.example.springTrain.user.JobSeekerRepository;
import com.example.springTrain.user.User;
import com.example.springTrain.user.UserRepository;
//import jakarta.validation.Valid;
//import validation.UserDTO;
import com.example.validation.ValidationError;

@Controller
public class RegisterController {
		 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private JobSeekerRepository jobSeekerRepository;
	@Autowired
	private UserService userService;
	
  @GetMapping("/search")
  public String search(@RequestParam("keyword") String keyword, Model model) {
      model.addAttribute("keyword", keyword);
      model.addAttribute("message", "Showing results for: " + keyword);
      return "home";
  }
	@GetMapping("/")
	public String getHomepage() {
		return "home";
	}
	
	// to register as employers
	  @GetMapping("/employers/register")
	  public String getemployform(Model model) {
		    model.addAttribute("employerDTO", new EmployerDTO()); // Ensure this matches the th:object in your form
	      return "employeeform";
	  }
	// to register as jobseekers
	  @GetMapping("/jobseekers/register")
	  public String getemployeeform(Model model) {
		    model.addAttribute("jobSeekerDTO", new JobSeekerDTO()); // Ensure this matches the th:object in your form
	      return "jobform";
	  }


	  @PostMapping("/jobseekers/register")
	  public String registerJobSeeker(@ModelAttribute JobSeekerDTO jobSeekerDTO, Model model) {
		  
		  	ValidationError validationError = new ValidationError();
			validationError.clear();
			
			//username should be unique in user table
			User existinguser = userRepository.findByUsername(jobSeekerDTO.getUsername());
			if(existinguser != null) {
				validationError.setUsername("Sorry username is already taken ");
				System.out.println("Sorry username is already taken " + jobSeekerDTO.getUsername()); // Debugging	
			}
			//email should be unique in job_seeker table
			JobSeeker existingjobemail = jobSeekerRepository.findByEmail(jobSeekerDTO.getEmail());
			if(existingjobemail != null) {
				validationError.setEmail("Sorry email is already taken ");
				System.out.println("Sorry email is already taken: " + jobSeekerDTO.getEmail()); // Debugging
			}	
			
			if(validationError.hasErrors()) {
				// Re-add the input data to the model to repopulate the form
		        model.addAttribute("jobSeekerDTO", jobSeekerDTO);
		        
				model.addAttribute("error", validationError);//sending both errors in 1 error variable
				return "jobform";
			}
			
	      // Create User entity
	      User user = new User();
	      user.setUsername(jobSeekerDTO.getUsername());
	      user.setPassword(jobSeekerDTO.getPassword());
	      user.setEmail(jobSeekerDTO.getEmail());
	      user.setUserType(jobSeekerDTO.getUsertype());


	      // Create JobSeeker entity
	      JobSeeker jobSeeker = new JobSeeker();
	      jobSeeker.setEmail(jobSeekerDTO.getEmail());
	      jobSeeker.setNumber(jobSeekerDTO.getNumber());
	      jobSeeker.setAddress(jobSeekerDTO.getAddress());
	      jobSeeker.setSkills(jobSeekerDTO.getSkills());
//      jobSeeker.setResume(jobSeekerDTO.getResume());

	      // Call the service method to save the user and job seeker
	      userService.createJobSeeker(user, jobSeeker);
	      
	      return "jobform";
	  }

	  @PostMapping("/employers/register")
	  public String registerJobSeeker(@ModelAttribute EmployerDTO employerDTO, Model model) {
		  
	  	ValidationError validationError = new ValidationError();
		validationError.clear();
		
		//username should be unique in user table
		User existinguser = userRepository.findByUsername(employerDTO.getUsername());
		if(existinguser != null) {
			validationError.setUsername("Sorry username is already taken ");
			System.out.println("Sorry username is already taken " + employerDTO.getUsername()); // Debugging	
		}
		//email should be unique in employer table
		User existingjobemail = userRepository.findByEmail(employerDTO.getEmail());
		if(existingjobemail != null) {
			validationError.setEmail("Sorry email is already taken ");
			System.out.println("Sorry email is already taken: " + employerDTO.getEmail()); // Debugging
		}	
		
		if(validationError.hasErrors()) {
			// Re-add the input data to the model to repopulate the form
	        model.addAttribute("employerDTO", employerDTO);
	      //sending both errors in 1 error variable
			model.addAttribute("error", validationError);
			return "employeeform";
		}
		  User user = new User();
	        user.setUsername(employerDTO.getCompanyName());
	        user.setPassword(employerDTO.getPassword());
	        user.setEmail(employerDTO.getEmail());
	        user.setUserType(employerDTO.getUsertype());


	        Employer employer = new Employer();
	        employer.setCompanyName(employerDTO.getCompanyName());
	        employer.setCompanyDescription(employerDTO.getCompanyDescription());
	        employer.setContactNumber(employerDTO.getContactNumber());
	        employer.setAddress(employerDTO.getAddress());
	        employer.setWebsite(employerDTO.getWebsite());
	        employer.setEmail(employerDTO.getEmail());

	        
	        userService.createEmployer(user, employer);

	        return "employeeform";
	  }
	
	//to login 
	@GetMapping("/login")
	public String getlogin() {
		return "login"; 
	}
	@PostMapping("/login")
	public String postlogin(
			@ModelAttribute UserDTO userDTO,
			Model model) {
		User ExistingUserAndPassword = userRepository.findByEmailAndPassword(userDTO.getEmail(),userDTO.getPassword());
		
		ValidationError validationError = new ValidationError();
		validationError.clear();
		
		if(ExistingUserAndPassword == null) {
			System.out.println("Sorry Credentials not matched" + userDTO.getEmail() + userDTO.getEmail()); // Debugging	
			validationError.setPassword("Sorry Credentials not matched");
		}
		
		if(validationError.hasErrors()) {
			// Re-add the input data to the model to repopulate the form
	        model.addAttribute("email", userDTO.getEmail());
	        model.addAttribute("password", userDTO.getPassword());
	        
			model.addAttribute("error", validationError);//sending server side error in 1 error variable
			return "login";
		}
		return "home"; 
	}

}
