package com.example.springTrain.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.SavedJobsService;
import com.example.springTrain.util.EnumConverter;


@Controller
public class ViewController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private SavedJobsService savedJobsService;
    
	//HomePage
	@GetMapping("/")
	public String getHomepage(Model model) {
		
	    List<String> jobCategories = Arrays.stream(jobPostingService.getAllCategories())
	            .map(category -> EnumConverter.toSentenceCase(category.name()))
	            .toList();
	    
		List<String> jobTypes = Arrays.stream(jobPostingService.getAllJobTypes())
				.map(type -> EnumConverter.toSentenceCase(type.name()))
				.toList();
		
		List<String>  experienceLevel = Arrays.stream(jobPostingService.getAllExperienceLevel())
				.map(exp -> EnumConverter.toSentenceCase(exp.name()))  
				.toList();
		
		List<String> cityLocation =Arrays.stream(jobPostingService.getAllCityLocation()) 
				.map(city -> EnumConverter.toSentenceCase(city.name()))
				.toList();

		List<Long> cityCount = jobPostingService.countJobPostingOfCityLocation(); 
		List<Long> typeCount = jobPostingService.countJobPostingOfJobType(); 
		List<Long> xpCount = jobPostingService.countJobPostingOfExpType(); 
		List<Long> categoryCount = jobPostingService.countJobPostingByJobCategory();

		long jobSeekerCount = jobSeekerService.countAlljobSeekers();
		long employerCount = employerService.countAllEmployers();
		long jobPostingCount = jobPostingService.countAllJobPosting();

		model.addAttribute("jobCategories", jobCategories);
		model.addAttribute("jobTypes", jobTypes);
		model.addAttribute("experienceLevel", experienceLevel);
		model.addAttribute("cityLocation", cityLocation);
		
		model.addAttribute("cityCount",cityCount);
		model.addAttribute("typeCount",typeCount);
		model.addAttribute("categoryCount",categoryCount);
		model.addAttribute("xpCount",xpCount);
		
		model.addAttribute("jobSeekerCount", jobSeekerCount);
		model.addAttribute("employerCount",employerCount);
		model.addAttribute("jobPostingCount",jobPostingCount);
		
		return "home";
	}
	
	//Dashboard which displays all data
	@GetMapping("/admin/view/dashboard")
	public String dashBoard(Model model) {
		
		 List<JobPosting> jobPosts = jobPostingService.findAllJobPostings();		 	
		 List<Employer> employers = employerService.findAllEmployers();
		 List<JobSeeker> jobseekers = jobSeekerService.findAllJobSeekers();

		 model.addAttribute("jobPosts",jobPosts); 
		 model.addAttribute("employers",employers);    
		 model.addAttribute("jobseekers",jobseekers);    

		 return "dashboard";
	}
	//display all lists of employers
	@GetMapping("/view/employers")
	public String listAllEmployers(Model model) {
		List<Employer> employers = employerService.findAllEmployers();
		model.addAttribute("employers",employers); 
		return "employers-list";
	}
		
	//display all lists of jobseekers
	@GetMapping("/view/jobseekers")
	public String listAllJobseekers(Model model) {
		 List<JobSeeker> jobSeekers = jobSeekerService.findAllJobSeekers();
		 model.addAttribute("jobSeekers",jobSeekers);	        
		return "jobseekers-list";
	}
	
	//display specific jobseekers profile
	@GetMapping("/view/jobseekers/profile/{jobSeekerId}")
	public String listSpecificJobseeker(Model model,
			@PathVariable ("jobSeekerId") Long jobSeekerId) {
		
		JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		model.addAttribute("jobSeeker",jobSeeker);	        
		return "jobseeker/jobseeker-profile";
	}
	
	//displaying jobPosts in Pages
    @GetMapping("/view/jobposts")
    public String getPaginatedJobPostings(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "9") int size,
            Model model) {
    	
    	// Get the employerId from Model
    	Long loggedInEmployerId = (Long) model.getAttribute("employerId");
    	
        //only shows jobPosts which are available
        Page<JobPosting> availablePost = jobPostingService.getPaginatedJobPostingInDesc(page, size);
        model.addAttribute("jobPosts", availablePost);

        //if same employer jobPosting and same loggedin employer jobPosting
        //edit and delete option available
        Employer employer  = employerService.findByEmployerId(loggedInEmployerId);
        model.addAttribute("employer", employer);

        if (loggedInEmployerId != null) { // Check if employerId is valid
           Employer loggedInEmployer  = employerService.findByEmployerId(loggedInEmployerId);
            
           if (loggedInEmployer != null) {
                model.addAttribute("loggedInEmployer", loggedInEmployer);
            }
        }
        return "jobpost";
    }
	
	//view Specific jobpost
	//login required
	//finds the object of the id which is only one
	//or sends jobposting not found
	  @GetMapping("/view/jobposts/details/{jobId}")
		public String viewSpecificJobPost(Model model,
				@PathVariable("jobId") Long jobId) {
			
		    JobPosting jobPost = jobPostingService.getJobPostingById(jobId);	
		    
		    Long jobSeekerId = (Long) model.getAttribute("jobSeekerId");
			JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
			
			if(jobSeeker != null) {	
				model.addAttribute("jobSeeker",jobSeeker);
			}

		    //getting applicationDeadline and checking remaining time
		    LocalDate applicationDeadline = jobPost.getApplicationDeadline();
		    if(applicationDeadline != null ) {
		    	String deadline = jobPostingService.getRemainingTime(applicationDeadline);
		    	
	    		model.addAttribute("canApply",true);
		    	//if deadline is set to "Deadline has passed" and jobPost isAvailable that means true true so change jobPost availability to false
		    	if( "Deadline has passed".equals(deadline) ) {
		    		jobPostingService.saveJobPostingAvailability(jobPost); // Save the updated job post
		    		model.addAttribute("canApply",false);
		    	}
		    	
		    	if(jobPost.isAvailable() == true) {
		    		model.addAttribute("available",true);
		    	}else {	
		    		model.addAttribute("available",false);
		    	}
		   
		    	model.addAttribute("deadlineDays", deadline);
		    }
		    
		    if(applicationDeadline == null) {
		    	model.addAttribute("available",false);
	    		model.addAttribute("canApply",false);
		    }
		    
		    //for apply and save options
		    JobApplication appliedjobPost = jobApplicationService.getJobApplicationByJobIdAndJobSekerId(jobId,jobSeekerId);
		    //if appliedjobPost is null we can apply but cannot apply when not null
		    if(appliedjobPost != null ) {    	
		    	model.addAttribute("appliedjobPost", appliedjobPost);   	
		    }

		    SavedJobs savedjobPost = savedJobsService.getJobPostingByJobIdAndJobSekerId(jobId,jobSeekerId);
		    if(savedjobPost != null) {
			    model.addAttribute("savedjobPost", savedjobPost);
		    }
		    
		    
		    model.addAttribute("jobPost", jobPost);	    
		    return "jobListing";  
		}
	
	//view all jobposts of specific employer profile and its post
	// employerId required
	//jobId required
	//no login required
	@GetMapping("/view/jobposts/{jobId}/of/employer/{employerId}")
		public String listAllJobPostsOfEmployer(Model model,
			@PathVariable ("jobId") Long jobId,
			@PathVariable ("employerId") Long employerId) {
				
		Long loggedInEmployerId = (Long) model.getAttribute("employerId");
		List<Long> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(employerId);
			
		Employer submittedEmployer = employerService.findByEmployerId(employerId);
		Employer loggedInEmployer = employerService.findByEmployerId(loggedInEmployerId);

		if (submittedEmployer != null) {
	        List<JobPosting> myJobPosts = jobPostingService.findByEmployerId(employerId);
	        model.addAttribute("employer",submittedEmployer);
	        model.addAttribute("myJobPosts", myJobPosts);
	    }
		
		if(loggedInEmployer != null) {
			JobPosting jobpost = jobPostingService.getByJobId(jobId);			
			Employer samejobEmployer = employerService.getByEmployerIdAndJobId(jobpost.getEmployer().getEmployerId(),jobId);
			
			if(samejobEmployer.equals(loggedInEmployer)) {
				model.addAttribute("loggedInEmployer",samejobEmployer);
			}
		}	
		
		model.addAttribute("jobAppCount",jobAppCount);
		return "employer/employer-profile";
	}	
	
	//view  employers profile
	//no login required
    @GetMapping("/view/employers/profile/{employerId}")
    public String viewEmployersProfile(Model model,
    		@PathVariable("employerId") Long employerId) {
    	
    	List<JobPosting> myJobPosts = jobPostingService.getJobPostingByEmployerId(employerId);		
		List<Long> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(employerId);
		Employer employer = employerService.findByEmployerId(employerId);
		
		model.addAttribute("jobAppCount",jobAppCount);	
		model.addAttribute("myJobPosts", myJobPosts);
		model.addAttribute("employer",employer);	
		
        return "employer/employer-profile";
    }
	
    @GetMapping("/view/terms")
    public String showTermsAndConditions() {
        return "info/terms"; 
    }
    @GetMapping("/view/privacy")
    public String showPrivacyPolicy() {
        return "info/privacy"; 
    }
    @GetMapping("/view/contact")
    public String showContactInfo() {
        return "info/contact"; 
    }

}
