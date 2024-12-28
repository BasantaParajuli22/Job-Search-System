package com.example.springTrain.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.entity.Employer;
import com.example.springTrain.entity.JobApplication;
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.service.SavedJobsService;


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
		
		JobCategory[] jobCategories = jobPostingService.getAllCategories();
		List<Integer> categoryCount = jobPostingService.countJobPostingByJobCategory();
		
		model.addAttribute("jobCategories", jobCategories);
		model.addAttribute("categoryCount",categoryCount);

		
		JobType[] jobTypes = jobPostingService.getAllJobTypes();
		List<Integer> typeCount = jobPostingService.countJobPostingOfJobType(); 
		
		model.addAttribute("jobTypes", jobTypes);
		model.addAttribute("typeCount",typeCount);

	
		ExperienceLevel[] experienceLevel = jobPostingService.getAllExperienceLevel();
		List<Integer> xpCount = jobPostingService.countJobPostingOfExpType(); 

		model.addAttribute("experienceLevel", experienceLevel);
		model.addAttribute("xpCount",xpCount);

		CityLocation[] cityLocation = jobPostingService.getAllCityLocation();
		List<Integer> cityCount = jobPostingService.countJobPostingOfCityLocation(); 
		
		model.addAttribute("cityLocation", cityLocation);
		model.addAttribute("cityCount",cityCount);

		
		long jobSeekerCount = jobSeekerService.countAlljobSeekers();
		long employerCount = employerService.countAllEmployers();
		long jobPostingCount = jobPostingService.countAllJobPosting();

		
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
			@PathVariable ("jobSeekerId") Integer jobSeekerId) {
		
		JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		model.addAttribute("jobSeeker",jobSeeker);	        
		return "jobseeker/jobseeker-profile";
	}
	
	//displaying jobPosts in Pages
	@GetMapping("/view/jobposts")
    public String getPaginatedJobPostings(
            @RequestParam(name ="page", defaultValue = "0") int page, 
            @RequestParam(name ="size", defaultValue = "9") int size, 
            Model model,
            @ModelAttribute ("employer") Employer employer) {
		
		//only shows jobPosts which are available
		Page<JobPosting> availablePost = jobPostingService.getPaginatedJobPostingInDesc(page, size);
       	model.addAttribute("jobPosts", availablePost);
       	
//        System.out.println("Total Pages: " + jobPostingPage.getTotalPages());
//        System.out.println("Current Page: " + jobPostingPage.getNumber());
//        System.out.println("Job Posts Content: " + jobPostingPage.getContent().size());
//        System.out.println("Is Empty: " + jobPostingPage.isEmpty());
        	
        //if same employer jobPosting and same loggedin employer jobPosting
        //edit and delete option available
        Integer employerId = employer.getEmployerId();
        if(employerId != null) {
        	Employer loggedInEmployer  = employerService.findByEmployerId(employerId); 
        	if(loggedInEmployer != null) {
        		model.addAttribute("loggedInEmployer",loggedInEmployer);
        	}else {
        		model.addAttribute("loggedInEmployer",null);
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
			@PathVariable("jobId") Integer jobId,
			@ModelAttribute("jobSeeker") JobSeeker jobSeeker) {
		
	    JobPosting jobPost = jobPostingService.getJobPostingById(jobId);	    
	    Integer jobSeekerId = jobSeeker.getJobSeekerId();
	    

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
	    	}else{
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
	    
	    // Fetch related jobs, for example by category or employer
		//List<JobPosting> relatedJobs = jobPostingService.findRelatedJobPostings(jobPost.getCategory(), jobPost.getEmployer().getUsers().getUserId());		 
		//model.addAttribute("relatedJobs", relatedJobs); 
	    
	    model.addAttribute("jobPost", jobPost);
	    
	    return "jobListing";  
	}
	
	//view all jobposts of specific employer and profile 
	// employerId required
	//jobId required
	//no login required
	@GetMapping("/view/jobposts/{jobId}/of/employer/{employerId}")
		public String listAllJobPostsOfEmployer(Model model,
			@PathVariable ("jobId") Integer jobId,
			@PathVariable ("employerId") Integer employerId,
			@ModelAttribute("employer")Employer employer) {
					
		List<Integer> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(employerId);
		model.addAttribute("jobAppCount",jobAppCount);
			
		Employer submittedEmployer = employerService.findByEmployerId(employerId);
		if (submittedEmployer != null) {
	        List<JobPosting> myJobPosts = jobPostingService.findByEmployerId(employerId);
	        model.addAttribute("employer",submittedEmployer);
	        model.addAttribute("myJobPosts", myJobPosts);
	    }
		
		if(employer!= null) {
			JobPosting jobpost = jobPostingService.getByJobId(jobId);			
			Employer samejobEmployer = employerService.getByEmployerIdAndJobId(jobpost.getEmployer().getEmployerId(),jobId);
			
			if(samejobEmployer.equals(employer)) {
				model.addAttribute("loggedInEmployer",samejobEmployer);
			}
		}		
		return "employer/employer-profile";
	}	
	
	//view  employers profile
	//no login required
    @GetMapping("/view/employers/profile/{employerId}")
    public String viewEmployersProfile(Model model,
    		@PathVariable("employerId") Integer employerId) {
    	
    	List<JobPosting> myJobPosts = jobPostingService.getJobPostingByEmployerId(employerId);		
		List<Integer> jobAppCount = jobApplicationService.countTotalApplicantsOfEmployer(employerId);
		Employer employer = employerService.findByEmployerId(employerId);
		
		model.addAttribute("jobAppCount",jobAppCount);	
		model.addAttribute("myJobPosts", myJobPosts);
		model.addAttribute("employer",employer);	
		
        return "employer/employer-profile";
    }
	
}
