package com.example.springTrain.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobApplicationService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobApplication;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.table.JobSeeker;
import com.example.springTrain.util.UserAuthorization;


@RequestMapping("/view")
@Controller
public class ViewController {

	
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private JobApplicationService jobApplicationService;
	
	
	//Dashboard which displays all data
	@GetMapping("/dashboard")
	public String dashBoard(Model model) {
		
		 List<JobPosting> jobPosts = jobPostingService.findAllJobPostings();		 	
		 List<Employer> employers = employerService.findAllEmployers();
		 List<JobSeeker> jobseekers = jobSeekerService.findAllJobSeekers();

		 System.out.println("JobPostings::" + jobPosts); 
		 System.out.println("Employers::::" + employers); 
		 System.out.println("jobseekers:::" + jobseekers); 

		 model.addAttribute("jobPosts",jobPosts); 
		 model.addAttribute("employers",employers);    
		 model.addAttribute("jobseekers",jobseekers);    

		 
		 
		 return "dashboard";
	}
	//display all lists of employers
	@GetMapping("employers")
	public String listAllEmployers(Model model) {
		List<Employer> employers = employerService.findAllEmployers();
		model.addAttribute("employers",employers); 
		return "employers-list";
	}
	//display specific employers profile
	@GetMapping("/employers/profile/{employerId}")
	public String listSpecificEmployer(Model model,
			@PathVariable ("employerId") Integer employerId) {
		
		Employer employer = employerService.findByEmployerId(employerId);
		model.addAttribute("employer",employer);	        
		return "employers-list";
	}
	
	
	//display all lists of jobseekers
	@GetMapping("/jobseekers")
	public String listAllJobseekers(Model model) {
		 List<JobSeeker> jobSeekers = jobSeekerService.findAllJobSeekers();
		 model.addAttribute("jobSeekers",jobSeekers);	        
		return "jobseekers-list";
	}
	//display specific jobseekers profile
	@GetMapping("/jobseekers/profile/{jobSeekerId}")
	public String listSpecificJobseeker(Model model,
			@PathVariable ("jobSeekerId") Integer jobSeekerId) {
		
		 JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		 model.addAttribute("jobSeeker",jobSeeker);	        
		return "jobseekers-list";
	}
	
	
	//view all jobposts without restrictions
	//no login required
	@GetMapping("/jobposts")
	public String listAllJobPostings(Model model) {
		
		 List<JobPosting> jobPosts = jobPostingService.findAllJobPostings();		 	
		String username = UserAuthorization.getLoggedInUsername();

		// If username is present, retrieve the corresponding JobSeeker
	    if (username != null) {
	        JobSeeker jobSeeker = jobSeekerService.findByUsername(username);
	        if (jobSeeker != null) {
	            model.addAttribute("jobSeeker", jobSeeker);
	        } else {
	            System.out.println("No JobSeeker found for username: " + username);
	            // Optionally handle this case if needed
	        }
	    } else {
	        System.out.println("No user is logged in.");
	    }
	    model.addAttribute("jobPosts",jobPosts); 
		return "jobpost";
	}
	
	//view Specific jobpost
	//no login required
	//finds the object of the id which is only one
	//or sends jobposting not found
	@GetMapping("/jobposts/details/{jobId}")
	public String viewSpecificJobPost(Model model,
			@PathVariable("jobId") Integer jobId) {
		
		// Fetch the job post by ID
	    JobPosting jobPost = jobPostingService.getJobPostingById(jobId);
	    
	    // Fetch related jobs, for example by category or employer
		//List<JobPosting> relatedJobs = jobPostingService.findRelatedJobPostings(jobPost.getCategory(), jobPost.getEmployer().getUsers().getUserId());		 
		// Add the job details to the model
		model.addAttribute("jobPost", jobPost);

		//model.addAttribute("relatedJobs", relatedJobs);  
		return "jobListing";  // Thymeleaf template
	}
	
	//view all jobposts of specific employer
	//by using employerId
	//no login required
	@GetMapping("/jobposts/of/employer/{employerId}")
		public String listAllJobPostsOfEmployer(Model model,
			@PathVariable ("employerId") Integer employerId) {
			
		//find companyName by employerId
		Employer employer = employerService.findByEmployerId(employerId);
		
		if (employer != null) {
			String companyName = employer.getCompanyName();
	        List<JobPosting> myJobPosts = jobPostingService.findAllJobPostingsByEmployer_CompanyName(companyName);
	        model.addAttribute("myJobPosts", myJobPosts);
	    } else {
	    	System.out.println("companyName not found");
	        return "login";
	    }
		return "employers-jobposts";
	}	
	
	//view all jobposts of mySelf only 
	//using own employerId 
	//login required
	@GetMapping("/jobposts/self/{employerId}")
	public String listAllMyJobPosts(Model model,
			@PathVariable ("employerId") Integer employerId) {
			
		String username = UserAuthorization.getLoggedInUsername();
		//loggedin companyName
		Employer employer = employerService.findByCompanyName(username);
		
		String companyName = employer.getCompanyName();
		//to findAllJobPostingsByEmployer
		List<JobPosting> myJobPosts =jobPostingService.findAllJobPostingsByEmployer_CompanyName(companyName);
		
		
		model.addAttribute("myJobPosts",myJobPosts);	        
		return "employers-jobposts";
	}		
		
	
	
	//view all lists of my JobPosts Applicants of an employer
	//login required
	//same employer can only see JobPosts Applicants
	@GetMapping("/applications/submittedto/employer/{employerId}")
	public String listAllJobApplicants(Model model,
			@PathVariable ("employerId") Integer employerId) {
			
		String username = UserAuthorization.getLoggedInUsername();
		//loggedin companyName
		Employer companyName = employerService.findByCompanyName(username);
		  
		//pathVariable comapanyName
		Employer urlCompanyName = employerService.findByEmployerId(employerId);

		if(!companyName.equals(urlCompanyName)) {	
			System.out.println("not same employerId so cannot view jobApplicants");
			return "redirect:/view/jobposts";
		}
		
		 List<JobApplication> allJobApplications = jobApplicationService.findAllJobApplicationByEmployer(companyName);
		if(allJobApplications == null) {
			return "redirect:/view/jobposts";
		}
		 model.addAttribute("allJobApplications",allJobApplications);	        
		return "application-all";
	}
	
	//view all lists of myJobPosts Applicants submitted  by the jobseeker
	//viewing all aplications i applied to 
	//login required
	//
	@GetMapping("application/submittedby/jobseeker/{jobSeekerId}")
	public String listAllOfMyAppliedJobPosts(Model model,
			@PathVariable ("jobSeekerId") Integer jobSeekerId) {
			
		//String username = UserAuthorization.getLoggedInUsername();
		//loggedin companyName
		JobSeeker jobSeeker = jobSeekerService.findByJobSeekerId(jobSeekerId);
		  
		List<JobApplication> allJobApplicants = jobApplicationService.findAllJobApplicationByJobSeeker(jobSeeker);
		if(allJobApplicants == null) {
			return "redirect:/view/jobposts";
		}

		model.addAttribute("allJobApplicants",allJobApplicants);	        
		return "jobseeker-applications";
	}
	
	// Update application status for a specific job application
	@PostMapping("/applications/submittedto/employer/statusUpdate")
	public String updateApplicationStatus(
	    @RequestParam("applicationId") Integer applicationId,
	    @RequestParam("applicationStatus") String applicationStatus,
	    @RequestParam("employerId") Integer employerId) {
		
		//calling to save changed status 
		jobApplicationService.updateStatus(applicationId,applicationStatus);
	    
	    // Redirect back to the list of job applications for the specific employer
	    return "redirect:/view/applications/submittedto/employer/" + employerId;
	}


	//view a certain jobseeker details for searching purposes
	
	
	//view jobposting of specific employer 
	//recieves and uses id of employer id  
	//finds the list of jobpostings from id
	//or sends jobposting not found 
	
	//view a certain employer details for searching purposes
		
}
