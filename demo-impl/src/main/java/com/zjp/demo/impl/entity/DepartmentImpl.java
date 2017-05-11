package com.zjp.demo.impl.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.util.ModelConvertor;

@Entity(name = "dept")
@Table(name = "dept")
public class DepartmentImpl implements Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OrderBy(value = "id ASC")
	private long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(length = 30)
	private String location;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private EmployeeImpl manager;

	@Column(name = "open_positions", nullable = false)
	private int openPositions;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_dept_id")
	private DepartmentImpl parentDept;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentDept")
	private List<DepartmentImpl> subDept;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "department")
	private List<EmployeeImpl> employees;

	public DepartmentImpl() {
		super();
	}

	public DepartmentImpl(String name, String location, Employee manager, int openPositions, Department parentDept) {
		super();
		this.name = name;
		this.location = location;
		this.manager = ModelConvertor.convertToEntity(manager);
		if (this.manager != null) {
			this.manager.setDepartment(this);
		}
		this.openPositions = openPositions;
		this.parentDept = ModelConvertor.convertToEntity(parentDept);
	}

	public DepartmentImpl(Department department) {
		super();
		this.name = department.getName();
		this.location = department.getLocation();
		this.manager = ModelConvertor.convertToEntity(department.getManager());
		if (manager != null) {
			manager.setDepartment(this);
		}
		this.openPositions = department.getOpenPositions();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@SuppressWarnings("unchecked")
	public EmployeeImpl getManager() {
		return manager;
	}

	public <T extends Employee> void setManager(T manager) {
		this.manager = ModelConvertor.convertToEntity(manager);
	}

	public int getOpenPositions() {
		return openPositions;
	}

	public void setOpenPositions(int openPositions) {
		this.openPositions = openPositions;
	}

	public Department getParentDept() {
		return parentDept;
	}

	public void setParentDept(DepartmentImpl parentDept) {
		this.parentDept = parentDept;
	}

	public List<DepartmentImpl> getSubDept() {
		return subDept;
	}

	public void setSubDept(List<DepartmentImpl> subDept) {
		this.subDept = subDept;
	}

	public List<EmployeeImpl> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeImpl> employees) {
		this.employees = employees;
	}

	public void update(Department newDept) {
		this.name = newDept.getName();
		this.location = newDept.getLocation();
		this.manager = ModelConvertor.convertToEntity(newDept.getManager());
		this.openPositions = newDept.getOpenPositions();
	}
}
