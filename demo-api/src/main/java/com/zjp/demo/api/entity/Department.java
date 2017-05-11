package com.zjp.demo.api.entity;

public interface Department {

	public long getId();

	public String getName();

	public String getLocation();

	public <T extends Employee> T getManager();

	public int getOpenPositions();
}
