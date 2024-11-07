package com.example.springTrain.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuthorization {
	
	//getting loggedin Username if it is loggedIn & authenticated
	public static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // Get the username of the logged-in user
    }
	
	//getting loggedin Username if it is loggedIn & authenticated
	//based on the role
	public static String getLoggedInJobSeekerUsername() {
	       return getUsernameIfRoleMatches("ROLE_JOBSEEKER");
   }
	
	public static String getLoggedInEmployerUsername() {
       return getUsernameIfRoleMatches("ROLE_EMPLOYER");
    }

	
	private static String getUsernameIfRoleMatches(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		//if authenticated
		if(authentication != null && authentication.isAuthenticated()) {
			//get all grantedAuthoruty of authentication
			for(GrantedAuthority authority : authentication.getAuthorities()) {
				//if authority.getAuthority equals JOBSEEKER or EMPLOYER
				if(authority.getAuthority().equals(role)) {
					//return username 
					return authentication.getName();
				}
			}
		}
		// return null if the role does not match
		return null;
	}
	
}
