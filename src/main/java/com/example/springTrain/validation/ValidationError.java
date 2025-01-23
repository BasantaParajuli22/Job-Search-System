package com.example.springTrain.validation;

public class ValidationError {

		private String companyName;
		private String email;
		private String password;		

		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void clear() {
			this.email = null;
			this.companyName = null;
			this.password = null;
		}
		//if email has   no value   inserted it will be null
		// if email not equals to null ==hasErrors
		public boolean hasErrors() {
			return email != null || companyName != null || password != null ;
		}
		
	}	
