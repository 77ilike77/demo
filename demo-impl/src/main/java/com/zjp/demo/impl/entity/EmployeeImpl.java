package com.zjp.demo.impl.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.api.entity.Gender;
import com.zjp.demo.impl.util.ModelConvertor;

@Entity(name = "employee")
@Table(name = "employee")
public class EmployeeImpl implements Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OrderBy(value = "id ASC")
	private long id;

	@Column(name = "first_name", nullable = false, length = 20)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 20)
	private String lastName;

	@Column(name = "ldap_user_name", nullable = false, length = 20)
	private String ldapUserName;

	@Column
	@Enumerated(value = EnumType.ORDINAL)
	private Gender gender;

	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Column(nullable = false, length = 30)
	private String title;

	@Column(nullable = false, length = 20)
	private String grade;

	/**
	 * EmployeeImpl.department not available in other types. Try
	 * setDepartment(DepartmentImpl department) to set it.
	 */
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private DepartmentImpl department;

	public EmployeeImpl() {
		super();
	}

	public EmployeeImpl(String firstName, String lastName, String ldapUserName, Gender gender, Date birthDate,
			String title, String grade, Department department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ldapUserName = ldapUserName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.title = title;
		this.grade = grade;
		this.department = ModelConvertor.convertToEntity(department);
	}

	public EmployeeImpl(Employee employee) {
		super();
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

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLdapUserName() {
		return ldapUserName;
	}

	public void setLdapUserName(String ldapUserName) {
		this.ldapUserName = ldapUserName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public DepartmentImpl getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentImpl department) {
		this.department = department;
	}

	public void update(Employee updateEmpl) {
		this.firstName = updateEmpl.getFirstName();
		this.lastName = updateEmpl.getLastName();
		this.ldapUserName = updateEmpl.getLdapUserName();
		this.gender = updateEmpl.getGender();
		this.birthDate = updateEmpl.getBirthDate();
		this.title = updateEmpl.getTitle();
		this.grade = updateEmpl.getGrade();
	}
}
