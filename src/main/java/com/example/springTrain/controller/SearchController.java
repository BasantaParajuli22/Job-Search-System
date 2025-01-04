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

import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;
import com.example.springTrain.service.JobPostingService;

@Controller
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobPostingService jobPostingService;
	
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
	 public String searchByCategoryId(@PathVariable("jobCategory") JobCategory jobCategory,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	         @RequestParam(name ="size", defaultValue = "9") int size, 
			 Model model) { 

        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByJobCategory(jobCategory,page, size);
        int totalPosts = jobPostingService.countJobPostingOfSpecificJobCategoryAndAvailable(jobCategory);
       
        model.addAttribute("jobPosts", jobPostingPage);        
        model.addAttribute("totalPosts",totalPosts);//to display total posts counts
	    model.addAttribute("filterName",jobCategory);//jobPosts category/type/location/Explvl/location
	    
	    return "jobpost";
	}
	
	//search all jobPostings by jobtype
	@GetMapping("/search/byjobtype/{jobType}")
	 public String searchByJobType(@PathVariable("jobType") JobType jobType, 
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	         @RequestParam(name ="size", defaultValue = "9") int size, 
			 Model model) { 
	    
        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByJobType(jobType,page, size);
        
        model.addAttribute("jobPosts", jobPostingPage);
	    model.addAttribute("filterName",jobType);
	    return "jobpost";
	}
	
	//search all jobPostings by experiencelevel
	@GetMapping("/search/byexperiencelevel/{expLevel}")
	 public String searchByExperiencelevel(@PathVariable("expLevel") ExperienceLevel expLevel,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	            @RequestParam(name ="size", defaultValue = "9") int size, 
	            Model model) { 
		
        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByExpLevel(expLevel,page, size);
        
        model.addAttribute("jobPosts", jobPostingPage);   
	    model.addAttribute("filterName",expLevel);
	    return "jobpost";
	}
	
	@GetMapping("/search/bylocation/{location}")
	 public String searchByLocation(@PathVariable("location") CityLocation location,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	            @RequestParam(name ="size", defaultValue = "9") int size, 
	            Model model) { 
		
	    Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByCityLocation(location,page, size);
       
	    model.addAttribute("jobPosts", jobPostingPage);
	    model.addAttribute("filterName",location);
	    return "jobpost";
	}
	

	
}
