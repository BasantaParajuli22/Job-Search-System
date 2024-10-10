package com.example.springTrain.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

	@GetMapping("/login")
	public String getLogin() {
	    return "login"; // Redirect back to login
	}
}
