package com.zjp.demo.impl.service;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.zjp.demo.api.constant.DemoConstants;
import com.zjp.demo.api.dto.DepartmentDTO;
import com.zjp.demo.api.dto.EmployeeDTO;
import com.zjp.demo.api.service.DemoService;
import com.zjp.demo.impl.manager.DepartmentManager;
import com.zjp.demo.impl.manager.EmployeeManager;
import com.zjp.demo.impl.util.ModelConvertor;

@Controller
public class DemoServiceImpl implements DemoService {

	@Autowired
	private DepartmentManager departmentManager;

	@Autowired
	private EmployeeManager employeeManager;

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	@Override
	public Response getDepartmentById(long id) {
		return createSuccessResponse(ModelConvertor.convertToDTO(departmentManager.getDepartmentById(id)));
	}

	@Override
	public Response getAllDepartments() {
		return createSuccessResponse(departmentManager.getAllDepartment());
	}

	@Override
	public Response getSubDepartmentsById(long id) {
		return createSuccessResponse(departmentManager.getSubDepartmentsById(id));
	}

	@Override
	public Response getParentDepartmentById(long id) {
		return createSuccessResponse(ModelConvertor.convertToDTO(departmentManager.getParentDepartmentById(id)));
	}

	@Override
	public Response getDepartmentEmployeesById(long id) {
		return createSuccessResponse(departmentManager.getDepartmentEmployeesById(id));
	}

	@Override
	public Response createSubDepartmentById(long id, @RequestBody DepartmentDTO subDept) {
		return createSuccessResponse(departmentManager.addDepartment(subDept, id));
	}

	@Override
	public Response createDepartment(@RequestBody DepartmentDTO subDept) {
		return createSuccessResponse(departmentManager.addDepartment(subDept));
	}

	@Override
	public Response updateDepartmentById(long id, @RequestBody DepartmentDTO newDept) {
		return createSuccessResponse(departmentManager.updateDepartmentById(newDept, id));
	}

	@Override
	public Response removeDepartmentById(long id) {
		return createSuccessResponse(departmentManager.removeDepartmentById(id));
	}

	@Override
	public Response getEmployeeById(long id) {
		return createSuccessResponse(ModelConvertor.convertToDTO(employeeManager.getEmployeeById(id)));
	}

	@Override
	public Response getAllEmployees() {
		return createSuccessResponse(employeeManager.getAllEmployee());
	}

	@Override
	public Response updateEmployeeById(long id, @RequestBody EmployeeDTO employee) {
		return createSuccessResponse(employeeManager.updateEmployeeById(employee, id));
	}

	@Override
	public Response removeEmployeeById(long id) {
		return createSuccessResponse(employeeManager.removeEmployeeById(id));
	}

	@Override
	public Response createEmployeeInDepartment(long deptId, @RequestBody EmployeeDTO employee) {
		return createSuccessResponse(employeeManager.addEmployeeInDepartment(employee, deptId));
	}

	private Response createSuccessResponse(Object entity) {
		return Response.ok().header(DemoConstants.MESSAGE, DemoConstants.SUCCESS).entity(entity).build();
	}
}
