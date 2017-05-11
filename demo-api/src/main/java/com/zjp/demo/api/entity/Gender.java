package com.zjp.demo.api.entity;

public enum Gender {
	Male, Female;

	public int value() {
		return this.ordinal();
	}

	public String toString() {
		return this.name();
	}
}
