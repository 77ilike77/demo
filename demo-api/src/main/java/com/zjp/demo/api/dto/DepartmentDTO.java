package com.zjp.demo.api.dto;

import com.zjp.demo.api.entity.Department;

public class DepartmentDTO implements Department {

	private long id;

	private String name;

	private String location;

	private EmployeeDTO manager;

	private int openPositions;

	public DepartmentDTO() {
		super();
	}

	public DepartmentDTO(long id, String name, String location, EmployeeDTO manager, int openPositions) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.manager = manager;
		this.openPositions = openPositions;
	}

	public DepartmentDTO(Department department) {
		super();
		this.id = department.getId();
		this.name = department.getName();
		this.location = department.getLocation();
		this.openPositions = department.getOpenPositions();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDTO getManager() {
		return manager;
	}

	public int getOpenPositions() {
		return openPositions;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setManager(EmployeeDTO manager) {
		this.manager = manager;
	}

	public void setOpenPositions(int openPositions) {
		this.openPositions = openPositions;
	}
}
