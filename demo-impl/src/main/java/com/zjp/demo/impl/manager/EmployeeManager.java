package com.zjp.demo.impl.manager;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zjp.demo.api.constant.DemoConstants;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.dao.DepartmentRepository;
import com.zjp.demo.impl.dao.EmployeeRepository;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;
import com.zjp.demo.impl.exception.DemoException;
import com.zjp.demo.impl.util.ModelConvertor;

@Repository
public class EmployeeManager {

	@Autowired
	private EmployeeRepository employeeRepo;

	public void setEmployeeRepo(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public void setDepartmentRepo(DepartmentRepository departmentRepo) {
		this.departmentRepo = departmentRepo;
	}

	@Autowired
	private DepartmentRepository departmentRepo;

	private Sort idSort = new Sort(Direction.ASC, "id");

	@Transactional(readOnly = true)
	public Employee getEmployeeById(long id) {
		EmployeeImpl employeeImpl = getEmployeeImplById(id);
		return ModelConvertor.convertToDTO(employeeImpl);
	}

	@Transactional(readOnly = true)
	public List<Employee> getAllEmployee() {
		List<EmployeeImpl> result = employeeRepo.findAll(idSort);
		return ModelConvertor.convertToDTOEmployeeList(result);
	}

	@Transactional
	public Employee updateEmployeeById(Employee updateEmpl, long id) {
		checkContentNotNull(updateEmpl);
		EmployeeImpl employeeImpl = getEmployeeImplById(id);
		employeeImpl.update(updateEmpl);
		return ModelConvertor.convertToDTO(employeeRepo.saveAndFlush(employeeImpl));
	}

	@Transactional
	public Employee removeEmployeeById(long id) {
		EmployeeImpl employeeImpl = getEmployeeImplById(id);
		employeeRepo.delete(id);
		return ModelConvertor.convertToDTO(employeeImpl);
	}

	@Transactional
	public Employee addEmployeeInDepartment(Employee employee, long deptId) {
		checkContentNotNull(employee);
		DepartmentImpl departmentImpl = getDepartmentImplById(deptId);
		EmployeeImpl employeeImpl = ModelConvertor.convertToEntity(employee);
		employeeImpl.setDepartment(departmentImpl);
		return ModelConvertor.convertToDTO(employeeRepo.saveAndFlush(employeeImpl));
	}

	private EmployeeImpl getEmployeeImplById(long id) {
		EmployeeImpl employeeImpl = employeeRepo.findOne(id);
		if (employeeImpl == null) {
			throw new DemoException(HttpStatus.NOT_FOUND.value(),
					MessageFormat.format(DemoConstants.NOT_FOUND, "Employee id = " + id));
		}
		return employeeImpl;
	}

	private DepartmentImpl getDepartmentImplById(long id) {
		DepartmentImpl departmentImpl = departmentRepo.findOne(id);
		if (departmentImpl == null) {
			throw new DemoException(HttpStatus.NOT_FOUND.value(),
					MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + id));
		}
		return departmentImpl;
	}

	private void checkContentNotNull(Employee employee) {
		if (employee == null) {
			throw new DemoException(HttpStatus.NO_CONTENT.value(),
					MessageFormat.format(DemoConstants.NO_CONTENT, "Employee"));
		}
	}
}
