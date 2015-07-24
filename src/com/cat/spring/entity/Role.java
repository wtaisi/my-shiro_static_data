/**
 * 
 */
package com.cat.spring.entity;

import java.util.ArrayList;
import java.util.List;

public class Role {
	private String name;
	private List<Permission> list=new ArrayList<Permission>();
	public Role() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public List<Permission> getList() {
		return list;
	}

	public void setList(List<Permission> list) {
		this.list = list;
	}

	public Role(String name, List<Permission> list) {
		super();
		this.name = name;
		this.list = list;
	}

	
}
