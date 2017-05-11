package com.zjp.demo.impl.util;

import java.util.ArrayList;
import java.util.List;

import com.zjp.demo.api.dto.DepartmentDTO;
import com.zjp.demo.api.dto.EmployeeDTO;
import com.zjp.demo.api.entity.Department;
import com.zjp.demo.api.entity.Employee;
import com.zjp.demo.impl.entity.DepartmentImpl;
import com.zjp.demo.impl.entity.EmployeeImpl;

public class ModelConvertor {

	public static DepartmentDTO convertToDTO(Department department) {
		if (department == null) {
			return null;
		}
		// Need to convert DepartmentDTO.manager to EmployeeDTO and set back
		EmployeeDTO employeeDTO = convertToDTO(department.getManager());
		DepartmentDTO departmentDTO = new DepartmentDTO(department);
		departmentDTO.setManager(employeeDTO);

		return departmentDTO;
	}

	public static EmployeeDTO convertToDTO(Employee employee) {
		if (employee == null) {
			return null;
		}
		return new EmployeeDTO(employee);
	}

	public static DepartmentImpl convertToEntity(Department department) {
		if (department == null) {
			return null;
		}
		return new DepartmentImpl(department);
	}

	public static EmployeeImpl convertToEntity(Employee employee) {
		if (employee == null) {
			return null;
		}
		return new EmployeeImpl(employee);
	}

	public static List<Department> convertToDTODepartmentList(List<DepartmentImpl> departments) {
		if (departments == null) {
			return null;
		}
		List<Department> result = new ArrayList<>(departments.size());
		for (DepartmentImpl department : departments) {
			result.add(convertToDTO(department));
		}
		return result;
	}

	public static List<Employee> convertToDTOEmployeeList(List<EmployeeImpl> employees) {
		if (employees == null) {
			return null;
		}
		List<Employee> result = new ArrayList<>(employees.size());
		for (EmployeeImpl employee : employees) {
			result.add(convertToDTO(employee));
		}
		return result;
	}
}
