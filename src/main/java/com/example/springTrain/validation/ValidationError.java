package com.example.springTrain.validation;

public class ValidationError {

		private String CompanyName;
		private String email;
		private String password;		

		public String getCompanyName() {
			return CompanyName;
		}
		public void setCompanyName(String companyName) {
			CompanyName = companyName;
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
			this.CompanyName = null;
			this.password = null;
		}
		//if email has   no value   inserted it will be null
		// if email not equals to null ==hasErrors
		public boolean hasErrors() {
			return email != null || CompanyName != null || password != null ;
		}
		
	}	
