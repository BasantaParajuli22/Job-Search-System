package com.example.springTrain.home;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.springTrain.dto.UserDTO; // Make sure to have a UserDTO class

@Controller
public class AuthController {

	@GetMapping("/login")
	public String getLogin() {
	    return "login"; // Redirect back to login

	}

	@PostMapping("/login")
	public String postLogin() {

	    return "home"; // Redirect back to login
	}

}
