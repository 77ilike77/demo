package com.zjp.demo.impl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.text.MessageFormat;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import com.zjp.demo.api.constant.DemoConstants;
import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.AbstractDemoUnitTest;
import com.zjp.demo.impl.dao.DepartmentRepository;
import com.zjp.demo.impl.dao.EmployeeRepository;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;
import com.zjp.demo.impl.exception.DemoException;
import com.zjp.demo.impl.manager.DepartmentManager;
import com.zjp.demo.impl.manager.EmployeeManager;
import com.zjp.demo.impl.util.ModelConvertor;

public class DemoServiceImplTest extends AbstractDemoUnitTest {

	private DemoServiceImpl demoService;

	private EmployeeManager employeeManager;
	private DepartmentManager departmentManager;
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

		departmentManager = new DepartmentManager();
		departmentManager.setDepartmentRepo(departmentRepo);

		demoService = new DemoServiceImpl();
		demoService.setDepartmentManager(departmentManager);
		demoService.setEmployeeManager(employeeManager);
	}

	@Test
	public void testGetDepartmentById() {
		Response response = demoService.getDepartmentById(departmentImpl.getId());
		checkResponseSuccess(response);
		checkDepartmentEquals(departmentImpl, (Department) response.getEntity());
	}

	@Test
	public void testGetDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.getDepartmentById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllDepartments() {
		Response response = demoService.getAllDepartments();
		List<Department> departments = (List<Department>) response.getEntity();

		assertTrue(departments.size() > 0);
		for (Department department : departments) {
			assertNotNull(department);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetSubDepartmentsById() {
		Response response = demoService.getSubDepartmentsById(departmentImpl.getId());
		List<Department> departments = (List<Department>) response.getEntity();

		assertTrue(departments.size() > 0);
		for (Department department : departments) {
			assertNotNull(department);
		}
	}

	@Test
	public void testGetSubDepartmentsByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.getSubDepartmentsById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testGetParentDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.getParentDepartmentById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testGetParentDepartmentByIdNoParent() {
		Response response = demoService.getParentDepartmentById(departmentImpl.getId());
		checkResponseSuccess(response);
		Department department = (Department) response.getEntity();
		assertNull(department);
	}

	@Test
	public void testGetParentDepartmentById() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(subDepartmentImpl);
		Response response = demoService.getParentDepartmentById(subDepartmentImpl.getId());
		checkResponseSuccess(response);
		Department department = (Department) response.getEntity();
		checkDepartmentEquals(departmentImpl, department);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDepartmentEmployeesById() {
		Response response = demoService.getDepartmentEmployeesById(departmentImpl.getId());
		List<Employee> employees = (List<Employee>) response.getEntity();

		assertTrue(employees.size() > 0);
		for (Employee employee : employees) {
			assertNotNull(employee);
		}
	}

	@Test
	public void testGetDepartmentEmployeesByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.getDepartmentEmployeesById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDepartmentEmployeesByIdNoEmployee() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(subDepartmentImpl);
		Response response = demoService.getDepartmentEmployeesById(departmentImpl.getId());
		List<Employee> employees = (List<Employee>) response.getEntity();
		assertNull(employees);
	}

	@Test
	public void testCreateSubDepartmentById() {
		when(departmentRepo.saveAndFlush(Matchers.any(DepartmentImpl.class))).thenReturn(subDepartmentImpl);
		Response response = demoService.createSubDepartmentById(departmentImpl.getId(),
				ModelConvertor.convertToDTO(subDepartmentImpl));
		Department department = (Department) response.getEntity();
		checkDepartmentEquals(subDepartmentImpl, department);
	}

	@Test
	public void testCreateSubDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.createSubDepartmentById(departmentImpl.getId(), ModelConvertor.convertToDTO(subDepartmentImpl));
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testCreateSubDepartmentByIdWithNull() {
		try {
			demoService.createSubDepartmentById(departmentImpl.getId(), null);
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Department"), e.getErrorMsg());
		}
	}

	@Test
	public void testCreateDepartment() {
		Response response = demoService.createDepartment(ModelConvertor.convertToDTO(departmentImpl));
		Department department = (Department) response.getEntity();
		checkDepartmentEquals(departmentImpl, department);
	}

	@Test
	public void testCreateDepartmentWithNull() {
		try {
			demoService.createDepartment(null);
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Department"), e.getErrorMsg());
		}
	}

	@Test
	public void testUpdateDepartmentById() {
		when(departmentRepo.saveAndFlush(Matchers.any(DepartmentImpl.class))).thenReturn(subDepartmentImpl);
		Response response = demoService.updateDepartmentById(departmentImpl.getId(),
				ModelConvertor.convertToDTO(subDepartmentImpl));
		checkResponseSuccess(response);
		Department department = (Department) response.getEntity();
		checkDepartmentEquals(subDepartmentImpl, department);
	}

	@Test
	public void testUpdateDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.updateDepartmentById(departmentImpl.getId(), ModelConvertor.convertToDTO(subDepartmentImpl));
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testUpdateDepartmentByIdWithNull() {
		try {
			demoService.updateDepartmentById(departmentImpl.getId(), null);
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Department"), e.getErrorMsg());
		}
	}

	@Test
	public void testRemoveDepartmentById() {
		Response response = demoService.removeDepartmentById(departmentImpl.getId());
		checkResponseSuccess(response);
		checkDepartmentEquals(departmentImpl, (Department) response.getEntity());
	}

	@Test
	public void testRemoveDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.removeDepartmentById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testGetEmployeeById() {
		Response response = demoService.getEmployeeById(employeeImpl.getId());
		checkResponseSuccess(response);
		checkEmployeeEquals(employeeImpl, (Employee) response.getEntity());
	}

	@Test
	public void testGetEmployeeByIdNotExist() {
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.getEmployeeById(employeeImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + employeeImpl.getId()),
					e.getErrorMsg());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllEmployees() {
		Response response = demoService.getAllEmployees();
		checkResponseSuccess(response);
		List<Employee> employees = (List<Employee>) response.getEntity();
		assertTrue(employees.size() > 0);
		for (Employee employee : employees) {
			assertNotNull(employee);
		}
	}

	@Test
	public void testUpdateEmployeeById() {
		when(employeeRepo.saveAndFlush(Matchers.any(EmployeeImpl.class))).thenReturn(otherEmployeeImpl);
		Response response = demoService.updateEmployeeById(employeeImpl.getId(),
				ModelConvertor.convertToDTO(otherEmployeeImpl));
		checkResponseSuccess(response);
		Employee employee = (Employee) response.getEntity();
		checkEmployeeEquals(otherEmployeeImpl, employee);
	}

	@Test
	public void testUpdateEmployeeByIdNotExist() {
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.updateEmployeeById(employeeImpl.getId(), ModelConvertor.convertToDTO(otherEmployeeImpl));
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + employeeImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testUpdateEmployeeByIdWithNull() {
		try {
			demoService.updateEmployeeById(employeeImpl.getId(), null);
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Employee"), e.getErrorMsg());
		}
	}

	@Test
	public void testRemoveEmployeeById() {
		Response response = demoService.removeEmployeeById(employeeImpl.getId());
		checkResponseSuccess(response);
		Employee employee = (Employee) response.getEntity();
		checkEmployeeEquals(employeeImpl, employee);
	}

	@Test
	public void testRemoveEmployeeByIdNotExist() {
		when(employeeRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.removeEmployeeById(employeeImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + employeeImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testCreateEmployeeInDepartment() {
		when(employeeRepo.saveAndFlush(Matchers.any(EmployeeImpl.class))).thenReturn(employeeImpl);
		Response response = demoService.createEmployeeInDepartment(departmentImpl.getId(),
				ModelConvertor.convertToDTO(employeeImpl));
		checkResponseSuccess(response);
		Employee employee = (Employee) response.getEntity();
		checkEmployeeEquals(employeeImpl, employee);
	}

	@Test
	public void testCreateEmployeeInDepartmentNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			demoService.createEmployeeInDepartment(departmentImpl.getId(), ModelConvertor.convertToDTO(employeeImpl));
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testCreateEmployeeInDepartmentWithNull() {
		try {
			demoService.createEmployeeInDepartment(departmentImpl.getId(), null);
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Employee"), e.getErrorMsg());
		}
	}

	private void checkResponseSuccess(Response response) {
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(DemoConstants.SUCCESS, response.getHeaders().get(DemoConstants.MESSAGE).get(0));
	}
}
