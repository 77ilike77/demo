package com.zjp.demo.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.zjp.demo.api.dto.DepartmentDTO;
import com.zjp.demo.api.dto.EmployeeDTO;
import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.api.entity.Gender;
import com.zjp.demo.impl.AbstractDemoUnitTest;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;

public class ModelConvertorTest extends AbstractDemoUnitTest {

	@Before
	public void before() throws Exception {
		mockDepartmentAndEmployee();
	}

	@Test
	public void testConvertToDTO() {
		Employee empl = null;
		assertNull(ModelConvertor.convertToDTO(empl));

		EmployeeDTO employeeDTO = ModelConvertor.convertToDTO(employee);
		assertEquals(ID, employeeDTO.getId());
		assertEquals(FIRST_NAME, employeeDTO.getFirstName());
		assertEquals(LAST_NAME, employeeDTO.getLastName());
		assertEquals(LDAP_NAME, employeeDTO.getLdapUserName());
		assertEquals(NEW_DATE, employeeDTO.getBirthDate());
		assertEquals(Gender.Male, employeeDTO.getGender());
		assertEquals(TITLE, employeeDTO.getTitle());
		assertEquals(GRADE, employeeDTO.getGrade());

		Department dept = null;
		assertNull(ModelConvertor.convertToDTO(dept));

		when(department.getManager()).thenReturn(employeeDTO);
		DepartmentDTO departmentDTO = ModelConvertor.convertToDTO(department);
		assertEquals(ID, departmentDTO.getId());
		assertEquals(LOCATION, departmentDTO.getLocation());
		assertEquals(NAME, departmentDTO.getName());
		checkEmployeeEquals(employeeDTO, departmentDTO.getManager());
		assertEquals(OPEN_POSITIONS, departmentDTO.getOpenPositions());
	}

	@Test
	public void testConvertToEntity() {
		Employee empl = null;
		assertNull(ModelConvertor.convertToEntity(empl));

		Department dept = null;
		assertNull(ModelConvertor.convertToEntity(dept));

		EmployeeImpl employeeImpl = ModelConvertor.convertToEntity(employee);
		assertEquals(ID_NONE, employeeImpl.getId());
		assertEquals(FIRST_NAME, employeeImpl.getFirstName());
		assertEquals(LAST_NAME, employeeImpl.getLastName());
		assertEquals(LDAP_NAME, employeeImpl.getLdapUserName());
		assertEquals(NEW_DATE, employeeImpl.getBirthDate());
		assertEquals(Gender.Male, employeeImpl.getGender());
		assertEquals(TITLE, employeeImpl.getTitle());
		assertEquals(GRADE, employeeImpl.getGrade());
		assertNull(employeeImpl.getDepartment());

		when(department.getManager()).thenReturn(employeeImpl);

		DepartmentImpl departmentImpl = ModelConvertor.convertToEntity(department);
		assertEquals(ID_NONE, departmentImpl.getId());
		assertEquals(LOCATION, departmentImpl.getLocation());
		assertEquals(NAME, departmentImpl.getName());
		checkEmployeeEquals(employeeImpl, departmentImpl.getManager());
		assertEquals(OPEN_POSITIONS, departmentImpl.getOpenPositions());
		assertNull(departmentImpl.getEmployees());
		assertNull(departmentImpl.getParentDept());
		assertNull(departmentImpl.getSubDept());
	}

	@Test
	public void testConvertToDTODepartmentList() {

		int size = 5;

		DepartmentImpl departmentImpl = ModelConvertor.convertToEntity(department);

		List<DepartmentImpl> departments = null;
		assertNull(ModelConvertor.convertToDTODepartmentList(departments));

		departments = new ArrayList<DepartmentImpl>(size);
		for (int i = 0; i < size; i++) {
			departments.add(departmentImpl);
		}

		List<Department> result = ModelConvertor.convertToDTODepartmentList(departments);
		assertEquals(size, result.size());
	}

	@Test
	public void testConvertToDTOEmployeeList() {
		int size = 5;

		EmployeeImpl employeeImpl = ModelConvertor.convertToEntity(employee);

		List<EmployeeImpl> employees = null;
		assertNull(ModelConvertor.convertToDTOEmployeeList(employees));

		employees = new ArrayList<EmployeeImpl>(size);
		for (int i = 0; i < size; i++) {
			employees.add(employeeImpl);
		}

		List<Employee> result = ModelConvertor.convertToDTOEmployeeList(employees);
		assertEquals(size, result.size());
	}
}
