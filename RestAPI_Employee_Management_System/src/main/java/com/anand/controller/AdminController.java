package com.anand.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anand.dto.APIResponse;
import com.anand.dto.SetPassword;
import com.anand.entity.Client;
import com.anand.entity.ContactDetails;
import com.anand.entity.Employee;
import com.anand.entity.Project;
import com.anand.service.ClientService;
import com.anand.service.EmployeeService;
import com.anand.service.ProjectService;


@RestController
//Prefix all the end Points in this controller with "/admin
@RequestMapping("/admin")
public class AdminController {
    
	@Autowired
	private EmployeeService employee_Services;
	
	@Autowired
	private ClientService client_Services;
	
	@Autowired
	private ProjectService project_Services;
	
	    /*----Admin With Employee----*/
	//Post Request to controller save employee in the databases;
	@PostMapping("/saveEmployee")
	public APIResponse<Employee> saveEmployee(@RequestBody Employee employee){
	      //pass the Employee Object and return APIResponse Object
			/*
			 * System.out.println("save employee------");
			 * System.out.println("employee Name:-->"+employee.getName());
			 * System.out.println("employee Deparment--->"+employee.getDepartment());
			 * System.out.println("employee email"+employee.getEmail());
			 * System.out.println("employee date-->"+employee.getJoiningDate());
			 * System.out.println("employee phone-->"+employee.getPhone());
			 */
		return employee_Services.save_Employee(employee);
	}
	//Get Request to find the Employee by EmployeeId
	@GetMapping("/getEmployeeByID/{employeeId}")
	public APIResponse<Employee> getEmployeeBYEmployeeID(@PathVariable("employeeId")
	String employeeId ){
		System.out.println("getting employee ByEmployee Id::");
		//call service method
		return employee_Services.get_EmployeeBYID(employeeId);
	}
	
	//get all Employee From the database
	@GetMapping("/getEmployees")
	public APIResponse<List<Employee>> get_All_Employee(){
		System.out.println("inside APIResponse of List Object");
		return employee_Services.getAllEmployees();
	}
	  
	
	//update the Employee
	@PutMapping("/updateEmployee/{employeeId}")
	public APIResponse<Employee> updateEmployee(@PathVariable("employeeId")
	String employeeId,@RequestBody Employee employee){
		return employee_Services.update_Employee(employeeId, employee);
	} 
	//delete the Employee
	@DeleteMapping("/deletEmployee/{employeeId}")
	public APIResponse<Employee> deleteEmployee(@PathVariable("employeeId")
	String employeeId){
		return employee_Services.delete_Employee(employeeId);
	}
	
	 //assign the Project to the Employee
	
	@PutMapping("/assignProject/employee/{employeeId}/project/{projectId}")
	public APIResponse<String> assignProject(@PathVariable("employeeId")
	String employeeId,@PathVariable ("projectId") String projectId){
		System.out.println("assign Project method");
		//employee Service
		return employee_Services.assign_Project(employeeId, projectId);
	}
	
	//set Password to the Employee 
	@PutMapping("/setEmployeePassword")
	public APIResponse<Employee> setEmployeePassword(@RequestBody SetPassword setPassword){
		 return employee_Services.set_Password(setPassword);
	}
	
	//release the Employee from project
	@PutMapping("/releaseEmployee/{employeeId}")
	public APIResponse<Employee> releaseEmployee(@PathVariable ("employeeId") String employeeId){
		return employee_Services.release_Employee_From_Project(employeeId);
	}
	
	
	//Get Project Detiels based on employeeID
	@GetMapping("/getProjectByEmployeeID/{employeeId}")
	public APIResponse<Project> getProjectByEmployeeID(@PathVariable("employeeId")
	String employeeId){
		return employee_Services.get_ProjectByEmployeeId(employeeId);
	}
	
	//get Employee which are on bench
	
	
	//date range and Email 
	
	
	/*---------Admin Controlling Client--------------*/
	
	//save the Client
	@PostMapping("/saveClient")
	public APIResponse<Client> saveClient(@RequestBody Client client){
		System.out.println("inside the save Client Method");
		return client_Services.save_Client(client);
	}
	
	//get the Client By  ClientId
	@GetMapping("/getClientByID/{clientId}")
	public APIResponse<Client> getClientBYID(@PathVariable("clientId") String clientId){
		return client_Services.get_ClientBYClientID(clientId);
	}
	
	//get List of Client
	@GetMapping("/getClients")
	public APIResponse<List<Client>> getAllClients(){
		return client_Services.get_ListOFEmployee();
	}
	  //delete the Client Pending
	
	//set password to the Client
	@PutMapping("/setClientPassword")
	public APIResponse<Client> setPasswordToClient(@RequestBody SetPassword setPassword){
		
		return client_Services.setPassword(setPassword);
	}
	
	//add Contact Details to the Client
	@PostMapping("/addContacts/{clientId}")
	public APIResponse<ContactDetails> addContact(@PathVariable("clientId") String clientId
			,@RequestBody ContactDetails contactDetails){
		 return client_Services.add_Contact(clientId, contactDetails);
	}
	
	/*
	 *  admin can get the project details from the client-id.
	 *   [a client can have more then one project.]
	 */
	@GetMapping("/getListOFProjectBYClientID/{clientId}")
	public APIResponse<List<Project>> getListOFProjectBYClientID(@PathVariable ("clientId")
	String clientId){
		return client_Services.get_ListOFProjectBYClientID(clientId);
	}
	
	//delete the Client 
	@DeleteMapping("/deleteClient/{clientId}")
	public APIResponse<Client> deleteClient(@PathVariable ("clientId")
	String clientId){
		return client_Services.delete_Client(clientId);
	}
	
	
	
	/*------------Admin Controlling Project-----------*/
	@PostMapping("/saveProject/{clientId}")
	public APIResponse<Project> saveProject(@PathVariable ("clientId")
	String clientId,@RequestBody Project project){
		System.out.println("inside Save Project Method");
		System.out.println("project name::"+project.getName());
		System.out.println("project EndDate::"+project.getEndDate());
		return project_Services.save_Project(clientId, project) ;
	}
	//update the Project 
	
	@PutMapping("/updateProject/{projectId}")
	public APIResponse<Project> updateProject(@PathVariable("projectId")
	String projectId,@RequestBody Project project){
		return project_Services.update_Project(projectId, project);
	}
	
	//Get the all Project
	@GetMapping("/getAllProjects")
	public APIResponse<List<Project>> getAllProject(){
		return project_Services.get_AllProjects();
	}
	//get the Project based on project Id
	@GetMapping("/getProjectByID/{projectId}")
	public APIResponse<Project> getProjectByID(@PathVariable("projectId")String projectId){
		return project_Services.get_ProjectONProjectID(projectId);
	}
    //get Client Information based On projectID
	@GetMapping("/getClientInfoByProjectID/{projectId}")
	public APIResponse<Client> getClientInfoByProjectID(@PathVariable("projectId")
	String projectId){
		return project_Services.get_ClientInfoByProjectID(projectId);
	}
	//admin can get all the employee on the basis of project-id
	@GetMapping("/getEmployeesBasedONProjectID/{projectId}")
   public APIResponse<List<Employee>> getEmployeesBasedONProjectID(@PathVariable("projectId")
   String projectId){
	    return project_Services.get_ListOFEmployeeBYProjectID(projectId);
   }
	//delete the Project is Pending
	@DeleteMapping("/deleteProject/{projectId}")
	public APIResponse<Project> deletProject(@PathVariable("projectId")
	String projectId){
		return project_Services.delete_Project(projectId);
	}
	
}
