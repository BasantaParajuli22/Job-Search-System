package com.example.springTrain.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthorization {
	
	public static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // Get the username of the logged-in user
    }
	
	public static String getLoggedInJobSeekerUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // Get the username of the logged-in user
    }
	
	public static String getLoggedInEmployerUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // Get the username of the logged-in user
    }
}
