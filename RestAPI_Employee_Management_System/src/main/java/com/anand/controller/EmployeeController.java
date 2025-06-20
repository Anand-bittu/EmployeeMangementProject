package com.anand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anand.dto.APIResponse;
import com.anand.entity.Employee;
import com.anand.entity.Project;
import com.anand.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employee_Service;
	
	@GetMapping("/getEmployeeDetails")
	public APIResponse<Employee> getEmployee(Authentication authentication){
		System.out.println("inside Get Employee Rest Controller");
		//get current User email
		String email=authentication.getName();
		return employee_Service.getEmployeeByEmailID(email);
	}
	
	@GetMapping("/getEmployeeProject")
	public APIResponse<Project> getProjectDetails(Authentication authentication){
		String email=authentication.getName();
		return employee_Service.get_Project_Based_On_Email(email);
	}
	
}
