package com.example.springTrain.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "job_categories")
public class JobCategories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cateoryId;
	
	private String categoryName;
	
	public int getCateoryId() {
		return cateoryId;
	}
	public void setCateoryId(int cateoryId) {
		this.cateoryId = cateoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
