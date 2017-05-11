package com.zjp.demo.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.Mockito;

import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.api.entity.Gender;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;

public class AbstractDemoUnitTest {
	protected static final long ID = 1L;
	protected static final long ID_NONE = 0L;
	protected static final String FIRST_NAME = "first_test";
	protected static final String LAST_NAME = "last_test";
	protected static final String LDAP_NAME = "ldap_test";
	protected static final Date NEW_DATE = new Date();
	protected static final String TITLE = "title_test";
	protected static final String GRADE = "grade_test";
	protected static final String ANOTHER = "_another";

	protected static final String LOCATION = "ChengDu";
	protected static final String NAME = "Test Department";
	protected static final int OPEN_POSITIONS = 3;
	protected static final String SUB = "_sub";

	protected Department department;
	protected Employee employee;

	protected DepartmentImpl departmentImpl;
	protected DepartmentImpl subDepartmentImpl;
	protected DepartmentImpl otherSubDepartmentImpl;
	protected EmployeeImpl employeeImpl;
	protected EmployeeImpl otherEmployeeImpl;

	protected List<DepartmentImpl> departmentImpls;
	protected List<DepartmentImpl> subDepartments;
	protected List<EmployeeImpl> employees;
	protected List<EmployeeImpl> deptEmployees;

	public void mockDepartmentAndEmployee() throws Exception {
		employee = Mockito.mock(Employee.class);
		when(employee.getId()).thenReturn(ID);
		when(employee.getFirstName()).thenReturn(FIRST_NAME);
		when(employee.getLastName()).thenReturn(LAST_NAME);
		when(employee.getLdapUserName()).thenReturn(LDAP_NAME);
		when(employee.getBirthDate()).thenReturn(NEW_DATE);
		when(employee.getGender()).thenReturn(Gender.Male);
		when(employee.getTitle()).thenReturn(TITLE);
		when(employee.getGrade()).thenReturn(GRADE);

		department = Mockito.mock(Department.class);
		when(department.getId()).thenReturn(ID);
		when(department.getLocation()).thenReturn(LOCATION);
		when(department.getName()).thenReturn(NAME);
		when(department.getOpenPositions()).thenReturn(OPEN_POSITIONS);
	}

	public void initDepartmentImplAndEmployeeImpl() {
		employeeImpl = new EmployeeImpl();
		employeeImpl.setFirstName(FIRST_NAME + ANOTHER);
		employeeImpl.setLastName(LAST_NAME);
		employeeImpl.setLdapUserName(LDAP_NAME);
		employeeImpl.setBirthDate(NEW_DATE);
		employeeImpl.setGender(Gender.Male);
		employeeImpl.setTitle(TITLE);
		employeeImpl.setGrade(GRADE);
		employeeImpl.setDepartment(departmentImpl);

		otherEmployeeImpl = new EmployeeImpl();
		otherEmployeeImpl.setFirstName(FIRST_NAME + ANOTHER);
		otherEmployeeImpl.setLastName(LAST_NAME + ANOTHER);
		otherEmployeeImpl.setLdapUserName(LDAP_NAME + ANOTHER);
		otherEmployeeImpl.setBirthDate(NEW_DATE);
		otherEmployeeImpl.setGender(Gender.Male);
		otherEmployeeImpl.setTitle(TITLE + ANOTHER);
		otherEmployeeImpl.setGrade(GRADE + ANOTHER);
		otherEmployeeImpl.setDepartment(subDepartmentImpl);

		employees = new ArrayList<>();
		employees.add(employeeImpl);
		employees.add(otherEmployeeImpl);

		deptEmployees = new ArrayList<>();
		deptEmployees.add(employeeImpl);
		deptEmployees.add(otherEmployeeImpl);

		departmentImpl = new DepartmentImpl();
		departmentImpl.setName(NAME);
		departmentImpl.setLocation(LOCATION);
		departmentImpl.setManager(employeeImpl);
		departmentImpl.setOpenPositions(OPEN_POSITIONS);
		departmentImpl.setEmployees(deptEmployees);

		subDepartmentImpl = new DepartmentImpl();
		subDepartmentImpl.setName(NAME + SUB);
		subDepartmentImpl.setLocation(LOCATION + SUB);
		subDepartmentImpl.setOpenPositions(OPEN_POSITIONS);
		subDepartmentImpl.setParentDept(departmentImpl);

		otherSubDepartmentImpl = new DepartmentImpl();
		otherSubDepartmentImpl.setName(NAME + ANOTHER + SUB);
		otherSubDepartmentImpl.setLocation(LOCATION + ANOTHER + SUB);
		otherSubDepartmentImpl.setOpenPositions(OPEN_POSITIONS);
		otherSubDepartmentImpl.setParentDept(departmentImpl);

		departmentImpls = new ArrayList<>();
		departmentImpls.add(departmentImpl);
		departmentImpls.add(subDepartmentImpl);
		departmentImpls.add(otherSubDepartmentImpl);

		subDepartments = new ArrayList<>();
		subDepartments.add(subDepartmentImpl);
		subDepartments.add(otherSubDepartmentImpl);
		departmentImpl.setSubDept(subDepartments);
	}

	protected void checkDepartmentEquals(Department department, Department dept) {
		assertEquals(department.getLocation(), dept.getLocation());
		assertEquals(department.getName(), dept.getName());
		assertEquals(department.getOpenPositions(), dept.getOpenPositions());
		if (department.getManager() != null || dept.getManager() != null) {
			checkEmployeeEquals(department.getManager(), dept.getManager());
		}
	}

	protected void checkEmployeeEquals(Employee employee, Employee empl) {
		assertEquals(employee.getBirthDate(), empl.getBirthDate());
		assertEquals(employee.getFirstName(), empl.getFirstName());
		assertEquals(employee.getLastName(), empl.getLastName());
		assertEquals(employee.getLdapUserName(), empl.getLdapUserName());
		assertEquals(employee.getGender(), empl.getGender());
		assertEquals(employee.getTitle(), empl.getTitle());
		assertEquals(employee.getGrade(), empl.getGrade());
	}
}
