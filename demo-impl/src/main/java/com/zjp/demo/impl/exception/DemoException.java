package com.zjp.demo.impl.exception;

public class DemoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int status;
	private String errorMsg;

	public DemoException() {
		super();
	}

	public DemoException(int status, String errorMsg) {
		super();
		this.status = status;
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
