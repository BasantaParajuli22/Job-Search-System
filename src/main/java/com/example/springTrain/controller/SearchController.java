package com.example.springTrain.controller;

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
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.util.EnumConverter;

@Controller
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobPostingService jobPostingService;
	@Autowired
	private EmployerService employerService;
	//Search by givng input can be 
	//title or
	//companyName
	@GetMapping("/search")
	public String getSearchByKeyword(Model model,
			@RequestParam(name ="page", defaultValue = "0") int page, 
	        @RequestParam(name ="size", defaultValue = "9") int size, 
			@RequestParam("keyword")String keyword) {

		Page<JobPosting> uniquejobPosts = jobPostingService.findAllJobPostingByKeyword(keyword,page,size);
		
	    model.addAttribute("jobPosts",uniquejobPosts);
		return "jobpost";
	}
	
	
	//takes categoryName to display categoryName
	@GetMapping("/search/byjobcategory/{jobCategory}")
	 public String searchByCategoryId(@PathVariable("jobCategory") String jobCategory,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	         @RequestParam(name ="size", defaultValue = "9") int size, 
			 Model model) { 

		//converting from string to enum 
		JobCategory category = EnumConverter.fromSentenceCase(jobCategory, JobCategory.class);
		 
        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByJobCategory(category,page, size);
        Long totalPosts = jobPostingService.countJobPostingOfSpecificJobCategoryAndAvailable(category);
       
        model.addAttribute("jobPosts", jobPostingPage);        
        model.addAttribute("totalPosts",totalPosts);//to display total posts counts
	    model.addAttribute("filterName",jobCategory);//jobPosts category/type/location/Explvl/location
	    
    	// Get the employerId from Model
    	Long loggedInEmployerId = (Long) model.getAttribute("employerId");
    	
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
	
	//search all jobPostings by jobtype
	@GetMapping("/search/byjobtype/{jobType}")
	 public String searchByJobType(@PathVariable("jobType") String jobType, 
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	         @RequestParam(name ="size", defaultValue = "9") int size, 
			 Model model) { 
	    
		JobType enumValue = EnumConverter.fromSentenceCase(jobType, JobType.class);
        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByJobType(enumValue,page, size);
        
        model.addAttribute("jobPosts", jobPostingPage);
	    model.addAttribute("filterName",jobType);
	    return "jobpost";
	}
	
	//search all jobPostings by experiencelevel
	@GetMapping("/search/byexperiencelevel/{expLevel}")
	 public String searchByExperiencelevel(@PathVariable("expLevel") String expLevel,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	            @RequestParam(name ="size", defaultValue = "9") int size, 
	            Model model) { 
		
		ExperienceLevel enumValue = EnumConverter.fromSentenceCase(expLevel, ExperienceLevel.class);
        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByExpLevel(enumValue,page, size);
        
        model.addAttribute("jobPosts", jobPostingPage);   
	    model.addAttribute("filterName",expLevel);
	    return "jobpost";
	}
	
	@GetMapping("/search/bylocation/{location}")
	 public String searchByLocation(@PathVariable("location") String location,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	            @RequestParam(name ="size", defaultValue = "9") int size, 
	            Model model) { 
		CityLocation enumValue = EnumConverter.fromSentenceCase(location, CityLocation.class);
	    Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByCityLocation(enumValue,page, size);
       
	    model.addAttribute("jobPosts", jobPostingPage);
	    model.addAttribute("filterName",location);
	    return "jobpost";
	}
	

	
}
