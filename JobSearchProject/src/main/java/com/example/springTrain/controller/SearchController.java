package com.example.springTrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springTrain.service.JobCategoryService;
import com.example.springTrain.service.JobPostingService;
import com.example.springTrain.table.JobCategory;
import com.example.springTrain.table.JobPosting;

@Controller
public class SearchController {
	
	@Autowired
	private JobCategoryService jobCategoryService;
	@Autowired
	private JobPostingService jobPostingService;
	//HomePage
	@GetMapping("/")
	public String getHomepage(Model model) {
		List<JobCategory> jobCategories = jobCategoryService.getAllCategories();
		model.addAttribute("jobCategory", jobCategories);
		return "home";
	}
	
	//by searching we dont change the database or anything 
	//so we use GetMapping 
	@GetMapping("/search")
	 public String getSearch(@RequestParam("keyword") String keyword, Model model) {
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("message", "Showing results for: " + keyword);
	    System.out.println("get search");
	    return "home";
	}
	
//	@PostMapping("/search")
//	 public String search(@RequestParam("keyword") String keyword, Model model) {
//	    model.addAttribute("keyword", keyword);
//	    model.addAttribute("message", "Showing results for: " + keyword);
//	    System.out.println("post search");
//
//	    return "home";
//	}
	
	//search all jobPostings by categoryId
	@PostMapping("/search/bycategory/{categoryId}")
	 public String searchByCategory(@RequestParam("keyword") String keyword,
			 @PathVariable("categoryId") Integer categoryId, Model model) { 
		model.addAttribute("keyword", keyword);
	    model.addAttribute("message", "Showing results for: " + keyword);
	    
	    List<JobPosting> jobPostings = jobPostingService.findAllJobPostingByCategoryId(categoryId);
	    model.addAttribute("jobPostings",jobPostings);
	    
	    return "home";
	}
	

}
