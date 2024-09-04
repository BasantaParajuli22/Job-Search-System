package validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
	
	@NotBlank(message = "user name is required")
	@Size(min = 3, max = 10, message = "usename must be 3 to 10 characters")
	private String username;
	
	@NotBlank(message = "user name is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;
	
	
	@NotBlank(message = "email cannot be blank")
	@Email(message ="email should be valid" )
	private String email;


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
}
 