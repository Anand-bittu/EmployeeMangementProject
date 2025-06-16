package com.anand.dto;

import java.util.List;

public class PersonDto {

	private String name;
	private long number;
	private List<AddressDto> listaddress;
	public PersonDto() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public List<AddressDto> getListaddress() {
		return listaddress;
	}
	public void setListaddress(List<AddressDto> listaddress) {
		this.listaddress = listaddress;
	}
    
	
	
}
