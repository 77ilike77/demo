package com.zjp.demo.impl.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.http.HttpStatus;

import com.zjp.demo.api.constant.DemoConstants;

public class ExceptionHandleProvider implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		System.out.println(exception.getMessage());
		return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.header(DemoConstants.MESSAGE, DemoConstants.FAILED).build();
	}
}
