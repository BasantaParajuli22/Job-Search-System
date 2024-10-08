//package com.example.springTrain.home;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class HomeController {
//
//    @GetMapping("/")
//    public String home() {
//        return "home";// This returns the home.html Thymeleaf template
//    }
//    @GetMapping("/login")
//    public String getlogin() {
//        return "login"; 
//    }
//    @PostMapping("/login")
//    public String postlogin() {
//        return "login"; 
//    }
//    
//    
//    @GetMapping("/jobseekers/register")
//    public String getregister() {
//        return "jobform"; 
//    }
//    @PostMapping("/jobseekers/register")
//    public String postregister(@RequestParam("username")String username, @RequestParam("email")String email ) {
//    	
//    	System.out.println("jobseeker entry");
//    	System.out.println(username);
//    	System.out.println(email);
//        return "jobform"; 
//    }
//    
//    @GetMapping("/employers/register")
//    public String getjobseekerRegister() {
//        return "employeeform";
//    }
//    @PostMapping("/employers/register")
//    public String postjobseekerRegister(@RequestParam("username")String username, @RequestParam("email")String email) {
//    	
//    	System.out.println("employer entry");
//    	System.out.println(username);
//    	System.out.println(email);
//        return "employeeform";
//    }
//    
//    @GetMapping("/search")
//    public String search(@RequestParam("keyword") String keyword, Model model) {
//        // For now, we'll just pass the keyword to the model and return the same page
//        // Later, you can implement search logic to find jobs based on the keyword
//
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("message", "Showing results for: " + keyword);
//
//        // Return the same home page with search results (you can also redirect to another page)
//        return "home";
//    }
//}
