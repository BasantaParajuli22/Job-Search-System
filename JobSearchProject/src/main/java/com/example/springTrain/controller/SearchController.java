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

import com.example.springTrain.dto.CityLocation;
import com.example.springTrain.dto.ExperienceLevel;
import com.example.springTrain.dto.JobType;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.JobCategoryService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;
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
	
	//@ModelAttribute Method: The addJobSeekerToModel() method 
	//will be executed before any request handler in this controller,
	// adding jobSeeker to the model if a user is logged in.
    @ModelAttribute
    public void addJobSeekerToModel(Model model) {
        String username = UserAuthorization.getLoggedInUsername();
        if (username != null) {
            JobSeeker jobSeeker = jobSeekerService.findByUsername(username);
            if (jobSeeker != null) {
                model.addAttribute("jobSeeker", jobSeeker);
            } else {
                logger.warn("No JobSeeker found for username: " + username);
            }
        }
    }
    
    
	//HomePage
	@GetMapping("/")
	public String getHomepage(Model model) {
		//it is options for selecting 
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
		return "home";
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
	    
		JobType[] jobTypes = JobType.values(); // Get all job types
		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByJobType(jobType);
	    
        model.addAttribute("jobTypes", jobTypes); // Add them to the model
	    model.addAttribute("jobPosts",jobPosts);

	    return "jobpost";
	}
	
	//search all jobPostings by experiencelevel
	@GetMapping("/search/byexperiencelevel/{expLevel}")
	 public String searchByExperiencelevel(@PathVariable("expLevel") ExperienceLevel expLevel, Model model) { 
	    
		ExperienceLevel[] experienceLevel  = ExperienceLevel.values(); // Get all experienceLevel
		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByExperienceLevel(expLevel);
	    
        model.addAttribute("jobTypes", experienceLevel); // Add them to the model
	    model.addAttribute("jobPosts",jobPosts);

	    return "jobpost";
	}
	
	@GetMapping("/search/bylocation/{location}")
	 public String searchByLocation(@PathVariable("location") CityLocation location, Model model) { 
		
	    List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByCityLocation(location);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	
	//using filter we will search
	@GetMapping("/search/filter")
	public String getSearchFilter(Model model) {
		//it is options for searching
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
		
		return "search";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//search all jobPostings by jobTitle
	@GetMapping("/search/bytitle/{title}")
	 public String searchByJobTitle(@PathVariable("title") String title, Model model) { 

		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByJobTitle(title);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	
	//search all jobPostings by companyName
	@GetMapping("/search/bycompanyName/{companyName}")
	 public String searchByCompanyName(@PathVariable("companyName") String companyName, Model model) { 
		
	    List<JobPosting> jobPosts = jobPostingService.findAllJobPostingByCompanyName(companyName);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	
	//this will all come under the filter
	//like if specified search like that
	//search all jobPostings by Salary
	@GetMapping("/search/bysalary/{salary}")
	 public String searchBySalary( @PathVariable("salary") String salary, Model model) { 

		List<JobPosting> jobPosts = jobPostingService.findAllJobPostingBySalary(salary);
	    model.addAttribute("jobPosts",jobPosts);
	    
	    return "jobpost";
	}
	


}
