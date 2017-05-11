package com.zjp.demo.impl.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import com.zjp.demo.api.constant.DemoConstants;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.AbstractDemoUnitTest;
import com.zjp.demo.impl.dao.DepartmentRepository;
import com.zjp.demo.impl.dao.EmployeeRepository;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;
import com.zjp.demo.impl.exception.DemoException;

public class EmployeeManagerTest extends AbstractDemoUnitTest {

	private EmployeeManager employeeManager;
	private EmployeeRepository employeeRepo;
	private DepartmentRepository departmentRepo;

	@Before
	public void before() throws Exception {
		initDepartmentImplAndEmployeeImpl();
		employeeRepo = Mockito.mock(EmployeeRepository.class);
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(employeeImpl);
		when(employeeRepo.findAll(Matchers.any(Sort.class))).thenReturn(employees);
		when(employeeRepo.saveAndFlush(Matchers.any(EmployeeImpl.class))).thenReturn(employeeImpl);

		departmentRepo = Mockito.mock(DepartmentRepository.class);
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(departmentImpl);
		when(departmentRepo.findAll(Matchers.any(Sort.class))).thenReturn(departmentImpls);
		when(departmentRepo.saveAndFlush(Matchers.any(DepartmentImpl.class))).thenReturn(departmentImpl);

		employeeManager = new EmployeeManager();
		employeeManager.setEmployeeRepo(employeeRepo);
		employeeManager.setDepartmentRepo(departmentRepo);
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = employeeManager.getEmployeeById(employeeImpl.getId());
		checkEmployeeEquals(employeeImpl, employee);
	}

	@Test
	public void testGetEmployeeByIdNotExist() {
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			employeeManager.getEmployeeById(employeeImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + employeeImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testGetAllEmployee() {
		List<Employee> employees = employeeManager.getAllEmployee();
		assertTrue(employees.size() > 0);
		for (Employee employee : employees) {
			assertNotNull(employee);
		}
	}

	@Test
	public void testUpdateEmployeeByIdNotExist() {
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			employeeManager.updateEmployeeById(otherEmployeeImpl, employeeImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + employeeImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testUpdateEmployeeById() {
		when(employeeRepo.saveAndFlush(Matchers.any(EmployeeImpl.class))).thenReturn(otherEmployeeImpl);
		Employee employee = employeeManager.updateEmployeeById(otherEmployeeImpl, employeeImpl.getId());
		checkEmployeeEquals(otherEmployeeImpl, employee);
	}

	@Test
	public void testRemoveEmployeeByIdNotExist() {
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			employeeManager.removeEmployeeById(employeeImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + employeeImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testRemoveEmployeeById() {
		Employee employee = employeeManager.removeEmployeeById(employeeImpl.getId());
		checkEmployeeEquals(employeeImpl, employee);
	}

	@Test
	public void testAddEmployeeWhenDepartmentNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			employeeManager.addEmployeeInDepartment(employeeImpl, departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testAddEmployeeInDepartment() {
		Employee employee = employeeManager.addEmployeeInDepartment(employeeImpl, departmentImpl.getId());
		checkEmployeeEquals(employeeImpl, employee);
	}

	@Test
	public void testAddEmployeeWithNull() {
		when(employeeRepo.saveAndFlush(null)).thenReturn(null);
		try {
			employeeManager.addEmployeeInDepartment(null, departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Employee"), e.getErrorMsg());
		}
	}
}
