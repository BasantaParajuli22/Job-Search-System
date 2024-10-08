package com.example.springTrain.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "job_categories")
public class Job_categories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cateory_id;
	
	private String category_name;
	
	
	public int getCateory_id() {
		return cateory_id;
	}
	public void setCateory_id(int cateory_id) {
		this.cateory_id = cateory_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
}
