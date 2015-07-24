package com.cat.spring.entity;

public class Permission {
	private String description;
	
	public Permission() {
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Permission(String description) {
		super();
		this.description = description;
	}
	@Override
	public String toString() {
		return "[" + description + "]";
	}
	
}
