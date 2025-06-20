package com.anand.utility;

import org.springframework.stereotype.Component;

import com.anand.entity.Employee;
import com.anand.repo.ClientRepo;
import com.anand.repo.EmployeeRepo;
import com.anand.repo.ProjectRepository;

@Component
public class IDGenerator {

	private static EmployeeRepo employeeRepo;
	private static ClientRepo clientRepo;
	private static ProjectRepository projectRepo;
	
	
	public IDGenerator(EmployeeRepo employeeRepo,ClientRepo clientRepo,ProjectRepository projectRepo) {
         System.out.println("inside the ID Generator with 3 parameter");
		 this.employeeRepo=employeeRepo;
         this.clientRepo=clientRepo;
         this.projectRepo=projectRepo;
	}
	//save the employee with Custom employeeId
	public static String generateEmployeeID() {
		  long count=  employeeRepo.count()+1;
		  return String.format("JTC-%03d",count);
	}
	//save the Client with Custom ClientID
	public static String generateClientID() {
		long count=clientRepo.count()+1;
		return String.format("CLIENT-%03d",count);
	}
	//save the Project with Custom ProjectID
	public static String generateProjectID() {
		long count=projectRepo.count()+1;
		return String.format("PROJECT-%03d",count);
	}
}
