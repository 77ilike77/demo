package com.zjp.demo.api.entity;

import java.util.Date;

public interface Employee {

	public long getId();

	public String getFirstName();

	public String getLastName();

	public String getLdapUserName();

	public Gender getGender();

	public Date getBirthDate();

	public String getTitle();

	public String getGrade();
}
