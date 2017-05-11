package com.zjp.demo.impl.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.AbstractDemoUnitTest;
import com.zjp.demo.impl.dao.DepartmentRepository;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.exception.DemoException;

public class DepartmentManagerTest extends AbstractDemoUnitTest {

	private DepartmentManager departmentManager;
	private DepartmentRepository departmentRepo;

	@Before
	public void before() throws Exception {
		initDepartmentImplAndEmployeeImpl();
		departmentRepo = Mockito.mock(DepartmentRepository.class);
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(departmentImpl);
		when(departmentRepo.findAll(Matchers.any(Sort.class))).thenReturn(departmentImpls);
		when(departmentRepo.saveAndFlush(Matchers.any(DepartmentImpl.class))).thenReturn(departmentImpl);

		departmentManager = new DepartmentManager();
		departmentManager.setDepartmentRepo(departmentRepo);
	}

	@Test
	public void testAddDepartmentNotExist() {
		try {
			departmentManager.addDepartment(null);
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NO_CONTENT.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NO_CONTENT, "Department"), e.getErrorMsg());
		}
	}

	@Test
	public void testAddDepartmentSuccess() {
		Department department = departmentManager.addDepartment(departmentImpl);
		checkDepartmentEquals(department, departmentImpl);
	}

	@Test
	public void testAddDepartmentSuccessWithId() {
		when(departmentRepo.saveAndFlush(Matchers.any(DepartmentImpl.class))).thenReturn(subDepartmentImpl);
		Department subDepartment = departmentManager.addDepartment(subDepartmentImpl, departmentImpl.getId());
		checkDepartmentEquals(subDepartment, subDepartmentImpl);
	}

	@Test
	public void testGetDepartmentById() {
		Department dept = departmentManager.getDepartmentById(departmentImpl.getId());
		checkDepartmentEquals(departmentImpl, dept);
	}

	@Test
	public void testGetDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			departmentManager.getDepartmentById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testGetAllDepartment() {
		List<Department> departments = departmentManager.getAllDepartment();
		assertTrue(departments.size() > 0);

		for (Department department : departments) {
			assertNotNull(department);
		}
	}

	@Test
	public void testGetParentDepartmentById() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(departmentImpl);
		assertNull(departmentManager.getParentDepartmentById(departmentImpl.getId()));

		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(subDepartmentImpl);
		Department parent = departmentManager.getParentDepartmentById(subDepartmentImpl.getId());
		assertNotNull(parent);
	}

	@Test
	public void testGetSubDepartmentsById() {
		List<Department> subs = departmentManager.getSubDepartmentsById(departmentImpl.getId());
		assertEquals(2, subs.size());
		for (Department dept : subs) {
			assertNotNull(dept);
		}

		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(subDepartmentImpl);
		subs = departmentManager.getSubDepartmentsById(subDepartmentImpl.getId());
		assertNull(subs);
	}

	@Test
	public void testGetDepartmentEmployeesById() {
		List<Employee> employees = departmentManager.getDepartmentEmployeesById(departmentImpl.getId());
		assertEquals(2, employees.size());
		for (Employee employee : employees) {
			assertNotNull(employee);
		}

		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(subDepartmentImpl);
		employees = departmentManager.getDepartmentEmployeesById(subDepartmentImpl.getId());
		assertNull(employees);
	}

	@Test
	public void testUpdateDepartmentById() {
		when(departmentRepo.saveAndFlush(Matchers.any(DepartmentImpl.class))).thenReturn(subDepartmentImpl);
		Department department = departmentManager.updateDepartmentById(subDepartmentImpl, departmentImpl.getId());
		checkDepartmentEquals(subDepartmentImpl, department);
	}

	@Test
	public void testUpdateDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			departmentManager.updateDepartmentById(departmentImpl, departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}

	@Test
	public void testRemoveDepartmentById() {
		Department subDepartment = departmentManager.removeDepartmentById(departmentImpl.getId());
		checkDepartmentEquals(departmentImpl, subDepartment);
	}

	@Test
	public void testRemoveDepartmentByIdNotExist() {
		when(departmentRepo.findOne(Matchers.anyLong())).thenReturn(null);
		try {
			departmentManager.removeDepartmentById(departmentImpl.getId());
			fail("Should be failed.");
		} catch (DemoException e) {
			assertEquals(HttpStatus.NOT_FOUND.value(), e.getStatus());
			assertEquals(MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + departmentImpl.getId()),
					e.getErrorMsg());
		}
	}
}
