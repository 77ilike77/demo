package com.zjp.demo.api.dto;

import java.util.Date;

import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.api.entity.Gender;

public class EmployeeDTO implements Employee {

	private long id;

	private String firstName;

	private String lastName;

	private String ldapUserName;

	private Gender gender;

	private Date birthDate;

	private String title;

	private String grade;

	public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(long id, String firstName, String lastName, String ldapUserName, Gender gender, Date birthDate,
			String title, String grade) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.ldapUserName = ldapUserName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.title = title;
		this.grade = grade;
	}

	public EmployeeDTO(Employee employee) {
		super();
		this.id = employee.getId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.ldapUserName = employee.getLdapUserName();
		this.gender = employee.getGender();
		this.birthDate = employee.getBirthDate();
		this.title = employee.getTitle();
		this.grade = employee.getGrade();
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLdapUserName() {
		return ldapUserName;
	}

	public Gender getGender() {
		return gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getTitle() {
		return title;
	}

	public String getGrade() {
		return grade;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLdapUserName(String ldapUserName) {
		this.ldapUserName = ldapUserName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
