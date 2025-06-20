package com.anand.dto;

//DTO Class To Receive password update request
public class SetPassword {
    
	//Holds the ID of the user (could be employee ID, client ID, etc.)
	private String id;
	//Holds the new Password to be set
	private String password;
	
	public SetPassword() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
