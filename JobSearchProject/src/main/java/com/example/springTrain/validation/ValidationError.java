package com.example.springTrain.validation;

public class ValidationError {

		private String username;
		private int Id;
		private String email;
		private String password;		

		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getId() {
			return Id;
		}
		public void setId(int id) {
			Id = id;
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
//		public String getLogin() {
//			return email;
//		}
//		public void setLogin(String email) {
//			this.email = email;
//		}	

		public void clear() {
			this.email = null;
			this.username = null;
			this.password = null;
		}
		//if email has no value inserted it will be null
		public boolean hasErrors() {
			return email != null || username != null || password != null ;
		}

	}	
