//package com.example.springTrain.home;
//
//import java.security.Principal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.service.UserService;
//import com.example.springTrain.user.Employer;
//import com.example.springTrain.user.JobSeeker;
//import com.example.springTrain.user.User;
//
//@Controller
//@RequestMapping("/users")
//public class ProfileController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/profile")
//    public String showProfile(Model model, Principal principal) {
//        User user = userService.findByUsername(principal.getName()); // Fetching user based on logged-in username
//        model.addAttribute("user", user); // Adding the user object to the model
//        model.addAttribute("userType", user.getUserType()); // Adding the user type (EMPLOYER or JOB_SEEKER)
//
//        // If the user is an employer, you might fetch employer-specific data
//        if (user instanceof Employer) {
//            Employer employer = (Employer) user; // Cast to Employer
//            model.addAttribute("employer", employer); // Add employer object to model
//        } else if (user instanceof JobSeeker) {
//            JobSeeker jobSeeker = (JobSeeker) user; // Cast to JobSeeker
//            model.addAttribute("jobSeeker", jobSeeker); // Optionally add job seeker object to model
//        }
//
//        return "profile"; // The name of the Thymeleaf template
//    }
//}
