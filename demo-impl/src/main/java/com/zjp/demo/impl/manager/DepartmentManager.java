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
import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.dao.DepartmentRepository;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;
import com.zjp.demo.impl.exception.DemoException;
import com.zjp.demo.impl.util.ModelConvertor;

@Repository
public class DepartmentManager {

	@Autowired
	private DepartmentRepository departmentRepo;

	public void setDepartmentRepo(DepartmentRepository departmentRepo) {
		this.departmentRepo = departmentRepo;
	}

	private Sort idSort = new Sort(Direction.ASC, "id");

	@Transactional(readOnly = true)
	public Department getDepartmentById(long id) {
		DepartmentImpl departmentImpl = getDepartmentImplById(id);
		Department department = ModelConvertor.convertToDTO(departmentImpl);
		return department;
	}

	@Transactional(readOnly = true)
	public List<Department> getAllDepartment() {
		List<DepartmentImpl> result = departmentRepo.findAll(idSort);
		return ModelConvertor.convertToDTODepartmentList(result);
	}

	@Transactional(readOnly = true)
	public List<Department> getSubDepartmentsById(long id) {
		DepartmentImpl departmentImpl = getDepartmentImplById(id);
		return ModelConvertor.convertToDTODepartmentList(departmentImpl.getSubDept());
	}

	@Transactional(readOnly = true)
	public Department getParentDepartmentById(long id) {
		DepartmentImpl departmentImpl = getDepartmentImplById(id);
		Department parentDepartment = ModelConvertor.convertToDTO(departmentImpl.getParentDept());
		return parentDepartment;
	}

	@Transactional(readOnly = true)
	public List<Employee> getDepartmentEmployeesById(long id) {
		DepartmentImpl departmentImpl = getDepartmentImplById(id);
		List<EmployeeImpl> employees = departmentImpl.getEmployees();
		return ModelConvertor.convertToDTOEmployeeList(employees);
	}

	@Transactional
	public Department addDepartment(Department subDept, long parentDeptId) {
		DepartmentImpl parentDept = getDepartmentImplById(parentDeptId);
		checkContentNotNull(subDept);
		DepartmentImpl departmentImpl = ModelConvertor.convertToEntity(subDept);
		departmentImpl.setParentDept(parentDept);
		return ModelConvertor.convertToDTO(departmentRepo.saveAndFlush(departmentImpl));
	}

	@Transactional
	public Department addDepartment(Department subDept) {
		checkContentNotNull(subDept);
		DepartmentImpl departmentImpl = ModelConvertor.convertToEntity(subDept);
		return ModelConvertor.convertToDTO(departmentRepo.saveAndFlush(departmentImpl));
	}

	@Transactional
	public Department updateDepartmentById(Department newDept, long deptId) {
		checkContentNotNull(newDept);
		DepartmentImpl departmentImpl = getDepartmentImplById(deptId);
		departmentImpl.update(newDept);
		return ModelConvertor.convertToDTO(departmentRepo.saveAndFlush(departmentImpl));
	}

	@Transactional
	public Department removeDepartmentById(long deptId) {
		DepartmentImpl departmentImpl = getDepartmentImplById(deptId);
		departmentRepo.delete(deptId);
		return ModelConvertor.convertToDTO(departmentImpl);
	}

	@Transactional(readOnly = true)
	private DepartmentImpl getDepartmentImplById(long id) {
		DepartmentImpl departmentImpl = departmentRepo.findOne(id);
		if (departmentImpl == null) {
			throw new DemoException(HttpStatus.NOT_FOUND.value(),
					MessageFormat.format(DemoConstants.NOT_FOUND, "Department id = " + id));
		}
		return departmentImpl;
	}

	private void checkContentNotNull(Department department) {
		if (department == null) {
			throw new DemoException(HttpStatus.NO_CONTENT.value(),
					MessageFormat.format(DemoConstants.NO_CONTENT, "Department"));
		}
	}
}
