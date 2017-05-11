package com.zjp.demo.api.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zjp.demo.api.dto.DepartmentDTO;
import com.zjp.demo.api.dto.EmployeeDTO;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("/demo")
public interface DemoService {

	@GET
	@Path("/dept/{id}")
	public Response getDepartmentById(@PathParam("id") long id);

	@GET
	@Path("/depts")
	public Response getAllDepartments();

	@GET
	@Path("/dept/{id}/subdepts")
	public Response getSubDepartmentsById(@PathParam("id") long id);

	@GET
	@Path("/dept/{id}/parentdept")
	public Response getParentDepartmentById(@PathParam("id") long id);

	@GET
	@Path("/dept/{id}/employees")
	public Response getDepartmentEmployeesById(@PathParam("id") long id);

	@POST
	@Path("/dept/{id}/subdept")
	public Response createSubDepartmentById(@PathParam("id") long id, DepartmentDTO subDept);

	@POST
	@Path("/dept")
	public Response createDepartment(DepartmentDTO subDept);

	@PUT
	@Path("/dept/{id}")
	public Response updateDepartmentById(@PathParam("id") long id, DepartmentDTO newDept);

	@DELETE
	@Path("/dept/{id}")
	public Response removeDepartmentById(@PathParam("id") long id);

	@GET
	@Path("/employee/{id}")
	public Response getEmployeeById(@PathParam("id") long id);

	@GET
	@Path("/employees")
	public Response getAllEmployees();

	@PUT
	@Path("/employee/{id}")
	public Response updateEmployeeById(@PathParam("id") long id, EmployeeDTO employee);

	@DELETE
	@Path("/employee/{id}")
	public Response removeEmployeeById(@PathParam("id") long id);

	@POST
	@Path("/dept/{id}/employee")
	public Response createEmployeeInDepartment(@PathParam("id") long id, EmployeeDTO employee);
}
