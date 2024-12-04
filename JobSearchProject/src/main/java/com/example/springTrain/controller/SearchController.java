package com.example.springTrain.controller;

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
import com.example.springTrain.entity.JobPosting;
import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.enums.CityLocation;
import com.example.springTrain.enums.ExperienceLevel;
import com.example.springTrain.enums.JobCategory;
import com.example.springTrain.enums.JobType;
import com.example.springTrain.security.UserAuthorization;
import com.example.springTrain.service.EmployerService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.service.JobSeekerService;

@Controller
public class SearchController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

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
    
//    @ModelAttribute
//    public void displayJobSearchOption(Model model) {
//		
//    }
    

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
		return "jobPost";
	}
	
	
	//takes categoryName to display categoryName
	@GetMapping("/search/byjobcategory/{jobCategory}")
	 public String searchByCategoryId(@PathVariable("jobCategory") JobCategory jobCategory,
			 @RequestParam(name ="page", defaultValue = "0") int page, 
	         @RequestParam(name ="size", defaultValue = "9") int size, 
			 Model model) { 

        Page<JobPosting> jobPostingPage = jobPostingService.getPaginatedJobPostingByJobCategory(jobCategory,page, size);
        model.addAttribute("jobPosts", jobPostingPage);
        
        //to display total posts counts
        Integer totalPosts = jobPostingService.countJobPostingOfSpecificJobCategory(jobCategory);
       
        model.addAttribute("totalPosts",totalPosts);
	    model.addAttribute("filterName",jobCategory);//jobPosts category/type/location/Explvl/location
	    
	    return "jobpost";
	}
	
	//search all jobPostings by jobType
	@GetMapping("/search/byjobType/{jobType}")
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
	
	
//	//Search by givng input can be title companyName
//	@GetMapping("/search/filter")
//	public String getSearchFilter(Model model) {
//		return "search";
//	}
	


}
