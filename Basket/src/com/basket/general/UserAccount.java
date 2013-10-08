package com.basket.general;

public class UserAccount {
	public UserAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public UserAccount() {
		// TODO Auto-generated constructor stub
	}
	private String username;
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
	private String password;
	
	
}
