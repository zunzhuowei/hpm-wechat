package com.keega.user.entity;

import com.keega.user.util.MyInject;

@MyInject
public class User {

	private String username;
	private String password;
	private String a0100;
	private String b0110;
	private String e0122;
	private String e01a1;
	private String token;

	public String getA0100() {
		return a0100;
	}

	public void setA0100(String a0100) {
		this.a0100 = a0100;
	}

	public String getB0110() {
		return b0110;
	}

	public void setB0110(String b0110) {
		this.b0110 = b0110;
	}

	public String getE0122() {
		return e0122;
	}

	public void setE0122(String e0122) {
		this.e0122 = e0122;
	}

	public String getE01a1() {
		return e01a1;
	}

	public void setE01a1(String e01a1) {
		this.e01a1 = e01a1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
