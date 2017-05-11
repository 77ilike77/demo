package com.zjp.demo.impl.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.zjp.demo.api.constant.DemoConstants;

public class DemoExceptionHandleProvider implements ExceptionMapper<DemoException> {

	@Override
	public Response toResponse(DemoException exception) {
		System.out.println(exception.getErrorMsg());
		return Response.status(exception.getStatus()).header(DemoConstants.MESSAGE, exception.getErrorMsg()).build();
	}
}
