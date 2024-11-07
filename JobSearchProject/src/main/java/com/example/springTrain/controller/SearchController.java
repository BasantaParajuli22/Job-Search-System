package com.example.springTrain.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.dto.CityLocation;
import com.example.springTrain.dto.ExperienceLevel;
import com.example.springTrain.dto.JobType;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobCategoryService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
import com.example.springTrain.table.Employer;
import com.example.springTrain.table.JobCategory;
import com.example.springTrain.table.JobPosting;
import com.example.springTrain.table.JobSeeker;

@Controller
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobCategoryService jobCategoryService;
	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private JobSeekerService jobSeekerService;
	@Autowired
	private EmployerService employerService;
	
	//@ModelAttribute Method: The addJobSeekerToModel() method 
	//will be executed before any request handler in this controller,
	// adding jobSeeker to the model if a user is logged in.
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
    
    @ModelAttribute
    public void displayJobSearchOption(Model model) {
		List<JobCategory> jobCategories = jobCategoryService.getAllCategories();
		model.addAttribute("jobCategory", jobCategories);
		
		JobType[] jobTypes = jobPostingService.getAllJobTypes();
		if(jobTypes!= null) {	
			model.addAttribute("jobTypes", jobTypes);
		}
		
		ExperienceLevel[] experienceLevel = jobPostingService.getAllExperienceLevel();
		if(experienceLevel!= null) {
			model.addAttribute("experienceLevel", experienceLevel);
		}
		
		CityLocation[] cityLocation = jobPostingService.getAllCityLocation();
		if(cityLocation!= null) {
			model.addAttribute("cityLocation", cityLocation);
		}
    }
    
	//HomePage
	@GetMapping("/")
	public String getHomepage(Model model) {
		return "home";
	}
	
	//Search by givng input can be title companyName
	@GetMapping("/search")
	public String getSearchByKeyword(Model model,
			@RequestParam("keyword")String keyword) {
		System.out.println(keyword);
		//Same title or companyName or skills could be found
		List<JobPosting> titlePosts = jobPostingService.findAllJobPostingByJobTitle(keyword);
		List<JobPosting> companyPosts = jobPostingService.findAllJobPostingByCompanyName(keyword);
		List<JobPosting> skillPosts = jobPostingService.findAllJobPostingBySkills(keyword);
		
		//need to make new list and add all to it
		//should be unique
		HashSet<JobPosting> uniquePosts = new HashSet<>();
		
		if(titlePosts != null) {
			uniquePosts.addAll(titlePosts);
		}
		if(companyPosts != null) {
			uniquePosts.addAll(companyPosts);
		}
		if(skillPosts != null) {
			uniquePosts.addAll(skillPosts);
		}

		//Converting unique HashSet back to ArrayList due to compatibility for model
	    List<JobPosting> allPosts = new ArrayList<>(uniquePosts);

	    if(uniquePosts != null) {
	    	model.addAttribute("jobPosts",allPosts);
	    }
		return "jobPost";
	}
	
	
	//search all jobPostings by categoryId
	//takes categoryName to display categoryName
	@GetMapping("/search/byjobcategoryName/{categoryName}")
	 public String searchByCategoryId(@PathVariable("categoryName") String categoryName,
			 Model model) { 

		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByCategoryName(categoryName);
	    
		model.addAttribute("jobPosts",jobPosts);
	    model.addAttribute("categoryName",categoryName);
	    
	    return "jobpost";
	}
	
	//search all jobPostings by jobType
	@GetMapping("/search/byjobType/{jobType}")
	 public String searchByJobType(@PathVariable("jobType") JobType jobType, Model model) { 
	    
		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByJobType(jobType);
	  
	    model.addAttribute("jobPosts",jobPosts);

	    return "jobpost";
	}
	
	//search all jobPostings by experiencelevel
	@GetMapping("/search/byexperiencelevel/{expLevel}")
	 public String searchByExperiencelevel(@PathVariable("expLevel") ExperienceLevel expLevel, Model model) { 
	    
		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByExperienceLevel(expLevel);
	    
	    model.addAttribute("jobPosts",jobPosts);

	    return "jobpost";
	}
	
	@GetMapping("/search/bylocation/{location}")
	 public String searchByLocation(@PathVariable("location") CityLocation location, Model model) { 
		
	    List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByCityLocation(location);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	
	
	//Search by givng input can be title companyName
	@GetMapping("/search/filter")
	public String getSearchFilter(Model model) {
		return "search";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//search all jobPostings by jobTitle
	@GetMapping("/search/bytitle")
	 public String searchByJobTitle(@RequestParam("title") String title, Model model) { 

		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByJobTitle(title);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	
	//search all jobPostings by companyName
	@GetMapping("/search/bycompanyName")
	 public String searchByCompanyName(@RequestParam("companyName") String companyName, Model model) { 
		
	    List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByCompanyName(companyName);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	
	//this will all come under the filter
	//like if specified search like that
	//search all jobPostings by Salary
	@GetMapping("/search/bysalary")
	 public String searchBySalary( @RequestParam("salary") String salary, Model model) { 

		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingBySalary(salary);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	


}
