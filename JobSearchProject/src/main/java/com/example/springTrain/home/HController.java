//package com.example.springTrain.home;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.springTrain.user.Employer;
//import com.example.springTrain.user.JobSeeker;
//import com.example.springTrain.user.EmployerRepository;
//import com.example.springTrain.user.JobSeekerRepository;
//
////import jakarta.validation.Valid;
////import validation.UserDTO;
//import validation.ValidationError;
//
//@Controller
//public class HController {
//	
//	 private static final Logger logger = LoggerFactory.getLogger(HController.class);
//	 
//	@Autowired
//	private EmployerRepository employerRepository;
//	@Autowired
//	private JobSeekerRepository jobSeekerRepository;
//	
//  @GetMapping("/search")
//  public String search(@RequestParam("keyword") String keyword, Model model) {
//      model.addAttribute("keyword", keyword);
//      model.addAttribute("message", "Showing results for: " + keyword);
//
//      // Return the same home page with search results (you can also redirect to another page)
//      return "home";
//  }
//	@GetMapping("/")
//	public String getHomepage() {
//		return "home";
//	}
//	
//	// to register as employers
//	  @GetMapping("/employers/register")
//	  public String getemployform() {
//	      return "employeeform";
//	  }
//	@PostMapping("/employers/register")
//	public String doRegistration(
//			@RequestParam("emp_username")String emp_username,
//			@RequestParam("emp_email")String emp_email,
//			@RequestParam("emp_number")String emp_number,
//			@RequestParam("emp_password")String emp_password,
//			Model model) {
//		
//		
//		ValidationError validationError = new ValidationError();
//		validationError.clear();
//		
//		Employer existingempuser = employerRepository.findByCompanyName(emp_username);//if found it will not be null
//		if(existingempuser != null) {//if same username foind in table u cant login 	
//			validationError.setUsername("Sorry username is already taken ");//if login is set it will be not null meaning it haserrors
//			//model.addAttribute("userExists","Sorry username is already taken");
//			System.out.println("Sorry username is already taken " + emp_username); // Debugging	
//		}
//		
//		Employer existingEmpPassword = employerRepository.findByPassword(emp_password);
//		if(existingEmpPassword != null) {
//			validationError.setEmail("Sorry password is already taken ");
//			//model.addAttribute("emailExists","Sorry email is already taken");
//			System.out.println("Sorry password is already taken: " + emp_password); // Debugging
//		}		
//		
//		if(validationError.hasErrors()) {
//			// Re-add the input data to the model to repopulate the form
//	        model.addAttribute("emp_username", emp_username);
//	        model.addAttribute("emp_email", emp_email);
//	        model.addAttribute("emp_number", emp_number);
//	         
//			model.addAttribute("error", validationError);//sending both errors in 1 error variable
//			return "employeeform";
//		}
//		
//		Employer employeruser = new Employer();
//		//calling methods of EmployerUser class
//		employeruser.setCompanyName(emp_username);
//		//employeruser.setEmail(emp_email);
//		employeruser.setContactNumber(emp_number);
//		employeruser.setPassword(emp_password);
////		employeruser.setUserType(UserType.USER);
//		
//		employerRepository.save(employeruser);//userRepository save employeruser
//		
//		System.out.println(emp_username);
//		System.out.println(emp_password);
//		logger.info(" employeruserRepository registration: " + emp_username);
//		
//		return "employeeform";
//	}
//	
//	// to register as jobseekers
//	  @GetMapping("/jobseekers/register")
//	  public String getemployeeform() {
//	      return "jobform";
//	  }
//	@PostMapping("/jobseekers/register")
//	public String postjobseekerform(
//			Model model,
//			@RequestParam("job_username")String job_username,
//			@RequestParam("job_email")String job_email,
//			@RequestParam("job_number")String job_number,
//			@RequestParam("job_password")String job_password) {
//
//		
//		ValidationError validationError = new ValidationError();
//		validationError.clear();
//		
//		JobSeeker existingjobuser = jobSeekerRepository.findByPassword(job_username);//if found it will not be null
//		if(existingjobuser != null) {//if same username foind in table u cant login 	
//			validationError.setUsername("Sorry username is already taken ");//if login is set it will be not null meaning it haserrors
//			//model.addAttribute("userExists","Sorry username is already taken");
//			System.out.println("Sorry username is already taken " + job_username); // Debugging	
//		}
//		
//		JobSeeker existingjobemail = jobSeekerRepository.findByEmail(job_email);
//		if(existingjobemail != null) {
//			validationError.setEmail("Sorry email is already taken ");
//			//model.addAttribute("emailExists","Sorry email is already taken");
//			System.out.println("Sorry email is already taken: " + job_email); // Debugging
//		}		
//		
//		if(validationError.hasErrors()) {
//			// Re-add the input data to the model to repopulate the form
//	        model.addAttribute("job_username", job_username);
//	        model.addAttribute("job_email", job_email);
//	        model.addAttribute("job_number", job_number);
//	        
//			model.addAttribute("error", validationError);//sending both errors in 1 error variable
//			return "jobform";
//		}
//		
//		JobSeeker jobuser = new JobSeeker();
//		//calling methods of User class
//		//jobuser.setUsername(job_username);
//		jobuser.setEmail(job_email);
//		jobuser.setNumber(job_number);
//		jobuser.setPassword(job_password);
//		jobSeekerRepository.save(jobuser);//userRepository save user
//		
//		//System.out.println(job_username); 
//		//logger.info("User registration: " + job_username);
//		
//		return "home";
//	}
//	
//	//to login 
//	@GetMapping("/login")
//	public String getlogin() {
//		return "login"; 
//	}
//	@PostMapping("/login")
//	public String postlogin(
//			@RequestParam("username")String username,
//			@RequestParam("password")String password,
//			Model model) {
//		
//		JobSeeker loginuserNpass = jobSeekerRepository.findByEmailAndPassword(username,password);
//		
//		ValidationError validationError = new ValidationError();
//		validationError.clear();
//		
//		if(loginuserNpass == null) {
//			System.out.println("Sorry Credentials not matched" + username); // Debugging	
//			validationError.setPassword("Sorry Credentials not matched");
//		}
//		
////		if(loginuserpass == null) {//if same password isnot foiund in table u cant login 	
////			validationError.setPassword("Sorry password didnot match");
////			validationError.setEmail("no email");
////			System.out.println("Sorry password didnot match " + password); // Debugging
////		}
//		if(validationError.hasErrors()) {
//			// Re-add the input data to the model to repopulate the form
//	        model.addAttribute("username", username);
//	        model.addAttribute("password", password);
//	        
//			model.addAttribute("error", validationError);//sending both errors in 1 error variable
//			return "login";
//		}
//		return "home"; 
//	}
//
//}
