package com.anand.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anand.dto.APIResponse;
import com.anand.dto.SetPassword;
import com.anand.entity.Employee;
import com.anand.entity.Project;
import com.anand.entity.RoleType;
import com.anand.entity.User;
import com.anand.repo.EmployeeRepo;
import com.anand.repo.ProjectRepository;

@Service
@Transactional  //all the method in these work with Transaction 
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//method to save a new Employee in the database
	public APIResponse<Employee> save_Employee(Employee employee){
		//first create the APIResponseObject so that you can send APIResponse 
		APIResponse<Employee> employeeApiResponse=new APIResponse<>();
		//check if the email already Exist or not in the database
		//here FindBy return optional Object
		if(employeeRepo.findByEmail(employee.getEmail()).isPresent()) {
			//if email already exists given the response by using APIResponse Class
			/*
			 * HttpStatus.CONFLICT.value(): This gets the integer value (which is 409) corresponding to the HTTP 409 Conflict status code.
			 *  This is an appropriate
			 *  HTTP status for a resource creation request where the resource already exists.
			 */
			employeeApiResponse.setStatusCode(HttpStatus.CONFLICT.value());  //409
			employeeApiResponse.setMessage("Email already Exist!!");
			employeeApiResponse.setData(null);
			return employeeApiResponse;
		}
		
		//if employee do not exist save the employee in the data base
		    employeeRepo.save(employee);
		    //now  save success status code 
		    employeeApiResponse.setStatusCode(HttpStatus.CREATED.value());//201
		    employeeApiResponse.setMessage("Employee is saved Succesfully !!");
		    //display saved employee Data as response
		    employeeApiResponse.setData(employee);
		return employeeApiResponse;
	}
	
	//method to get Employee Based on EmployeeID
	public APIResponse<Employee> get_EmployeeBYID(String employeeId){
		APIResponse<Employee> empAPIResponse=
				new APIResponse<>();
		   //now get the Employee by EmployeeID
		 Optional<Employee> employee = employeeRepo.findById(employeeId);
		   //check if the employee is found or not
		 if(employee.isPresent()) {
			 empAPIResponse.setStatusCode(HttpStatus.OK.value());  //200
			 empAPIResponse.setMessage("Employee Found !!!");
			 empAPIResponse.setData(employee.get());
		 }
		 else {
			 empAPIResponse.setStatusCode(HttpStatus.NOT_FOUND.value()); //404
			 empAPIResponse.setMessage("Employee Does not exist with These employeeID::"+employeeId);
		     empAPIResponse.setData(null);
		 }
		return empAPIResponse;
	}
	
	//Get all Employee From the database
	public APIResponse<List<Employee>> getAllEmployees(){
		APIResponse<List<Employee>> listAPIResponse=
				new APIResponse<>();
		//now get all the Employees
		   List<Employee> listEmployee = employeeRepo.findAll();
		   if(listEmployee.isEmpty()) {
			   listAPIResponse.setStatusCode(HttpStatus.NOT_FOUND.value()); //404
			   listAPIResponse.setMessage("No Employee aviable");
		   }else {
			   listAPIResponse.setStatusCode(HttpStatus.OK.value()); //200
			   listAPIResponse.setMessage("Employee are aviable");
			   //Sort the Employee based on the decending order  based on  Date 
			    List<Employee> sortedEmployee=listEmployee.stream()
			       .sorted(Comparator.comparing(Employee::getJoiningDate).reversed())
			       .collect(Collectors.toList());
			   listAPIResponse.setData(sortedEmployee);
		   }
		return listAPIResponse;
	}
	//Method to assign project to the Employee By projectID and Employee Id
	public APIResponse<String> assign_Project(String  employeeId,String projectId){
		  //first create the APIResponse
		APIResponse<String> apiResponse=new 
				APIResponse<>();
		//First Get the Employee By EmployeeId
		Optional<Employee> employeeByID=employeeRepo.findById(employeeId);
		
		//check if the employeeByID is Empty are not
		if(employeeByID.isEmpty()) {
			apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());//404
			apiResponse.setMessage("NO employee is Found by these ID-->"+employeeId);
			return apiResponse; //return apiResponse
		}
		//now get the Project by ProjectID
		   Optional<Project> projectByID=projectRepo.findById(projectId);
		   //now check the Condtion of Project
		   if(projectByID.isEmpty()) {
			   apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value()); //404
			   apiResponse.setMessage("No Project is Found by these ID--->"+projectId);
			   return apiResponse; //return apiResponse
		   }
		   
		   //if all good get the Employee from the Optional class
		   Employee employee=employeeByID.get();
		   //now check if the employee is already assigned with the project 
		   if(employee.getProject()!=null) {
			   apiResponse.setStatusCode(HttpStatus.CONFLICT.value()); //409 //conflict
			   apiResponse.setMessage("Employee is already assigend with the Project Id-->"+projectId);
		         return apiResponse;
		   }
		   //now if all good assign the project to the employee
		   employee.setProject(projectByID.get());
		   //now save the employee with these project in the database
		   employeeRepo.save(employee);
		  //now send the succesfull msg to the end User
		   apiResponse.setStatusCode(HttpStatus.OK.value()); //200
		   apiResponse.setMessage("Project is assigned to the Employee!!");
		  //now Return api Responses
		return apiResponse;
	}
	
	//method to set the password to the employee
	 public APIResponse<Employee> set_Password(SetPassword passwordRequest){
		  //create the APIResponse object First 
		 APIResponse<Employee> apiResponse=
				 new APIResponse<>();
		 //now Get the Employee By EmployeeID
		  Optional<Employee> employeeByID=employeeRepo.findById(passwordRequest.getId());
		   //if employee Not Found 
		  if(employeeByID.isEmpty()) {
			  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());//404
			  apiResponse.setMessage("Employee does not exists with This EmployeeID-->"+passwordRequest.getId());
			  apiResponse.setData(null);
			  return apiResponse; //return apiRespose first
		  }
		  //now if all good get the Employee from the Optinal Object
		  Employee employee=employeeByID.get();
		  /*now get the User object from this employee
		   *  as in employee Entity has One to one mapping with the user Object
		   */
		  User user=employee.getUser();
		 //if user is new then
		  if(user==null) {
			  //create the new User with these Employee Object
			  user=new User();
			  //first set Email 
			  user.setEmail(employee.getEmail());
			  //now set the Role of the employee
			    user.setRole(RoleType.EMPLOYEE);
		  }
		    //now set the password to the Employee in bycrpted form
		     user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
		     //now set the User to the Employee
		     employee.setUser(user);
		     //now save these Employee Object
		     employeeRepo.save(employee);
		     //now Save ResponseAPI
		     apiResponse.setStatusCode(HttpStatus.OK.value());//200
		     apiResponse.setMessage("Password is saved For these Employee::"+passwordRequest.getId());
		     apiResponse.setData(employee);
		     //now send this APIResponse
		 return apiResponse;
	 }
	 
	 //get the Employee Based on EmployeeEmail ID
	 public APIResponse<Employee> getEmployeeByEmailID(String email){
		 //create Resposne
		 APIResponse<Employee> apiResponse=new APIResponse<>();
		 //get the Employee By Employee emailID
		 Optional<Employee> employee=employeeRepo.findByEmail(email);
		 if(employee.isPresent()) {
			 apiResponse.setStatusCode(HttpStatus.OK.value());//200
			 apiResponse.setMessage("Employee Found By these Email::"+email);
			 apiResponse.setData(employee.get());
		 }else {
			    //if not found 404
			 apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			 apiResponse.setMessage("Employee Do not exists with these Email");
		 }  
		 return apiResponse;
	 }
	 
	 //method to get project based on Employee Email Id
	 public APIResponse<Project> get_Project_Based_On_Email(String email){
	    APIResponse<Project> apiResponses=new
	    		APIResponse<>();
	    //get the Employee by email ID
	    Optional<Employee> employee=employeeRepo.findByEmail(email);
	    if(employee.isEmpty()) {
	    	apiResponses.setStatusCode(HttpStatus.NOT_FOUND.value());
	    	apiResponses.setMessage("Employee do not exists With these email::"+email);
	    	return apiResponses;
	    }
	    //if all good get the Project form Employee
	   Project project =employee.get().getProject();
	   if(project==null) {
		   apiResponses.setStatusCode(HttpStatus.NOT_FOUND.value());
		   apiResponses.setMessage("Employee is On Bench!!");
		   return apiResponses;
	   }
	   //if all good send succesfull Message 
	   apiResponses.setStatusCode(HttpStatus.OK.value());
	   apiResponses.setMessage("Project Details for Employee-->"+employee.get().getEmployeeId());
	   apiResponses.setData(project); 
		 return apiResponses;
	 }
	 //release the Employee from the Project
	 public APIResponse<Employee> release_Employee_From_Project(String employeeId){
		//Create the API first
		 APIResponse<Employee> apiResponse=new
				 APIResponse<>();
		 //first get the Employee 
		 Optional<Employee> employeeByID = employeeRepo.findById(employeeId);
		 if(employeeByID.isEmpty()) {
			 apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			 apiResponse.setMessage("Employee Not Found with the ID::"+employeeId);
			 return apiResponse;
		 }
		 //now get actual Employee
		  Employee employee=employeeByID.get();
		  //if employee is already on Beach 
		  if(employee.getProject()==null) {
			 apiResponse.setStatusCode(HttpStatus.CONFLICT.value());//409 
			 apiResponse.setMessage("Employee is already on beanch");
			 return apiResponse;
		  }
		  //now remove the Employee from the Project by assign Null 
		  employee.setProject(null);
		  //now save these employee Object on the database
		  employeeRepo.save(employee);
		  //return succsefull message
		  apiResponse.setStatusCode(HttpStatus.OK.value());//200
		  apiResponse.setMessage("Employee is released from Project");
		  apiResponse.setData(employee); 
		 return apiResponse;
	 }
	 //Method to update the Employee 
	  public APIResponse<Employee> update_Employee(String employeeId,
			  Employee employeeRequest){  
		  //first Create API Response
		  APIResponse<Employee> apiResponse=new
				  APIResponse<>();
		  //first Get the Existing Employee 
	   Optional<Employee> existingEmployeeByID=employeeRepo.findById(employeeId);
	   if(existingEmployeeByID.isEmpty()) {
		   apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value()); //404
		   apiResponse.setMessage("Employee do not exists with this ID-->"+employeeId);
		   return apiResponse;
	   }
	   //now get Employee from the Optional Class
	        Employee existingEmployee=existingEmployeeByID.get();
		  //now update Only name,department,phone   
	        if(existingEmployee!=null) {
	        	//update Name from employeeRequest
	        	if(employeeRequest.getName()!=null) 
	        		existingEmployee.setName(employeeRequest.getName());
	        	 if(employeeRequest.getDepartment()!=null)
	        		 existingEmployee.setDepartment(employeeRequest.getDepartment());
	        	 if(employeeRequest.getPhone()!=null)
	        		 existingEmployee.setPhone(employeeRequest.getPhone());
	        	 //now save these existingEmployee in the data base
	        	 employeeRepo.save(existingEmployee);
	        	//return succesfull message
	        	 apiResponse.setStatusCode(HttpStatus.OK.value());
	        	 apiResponse.setMessage("Employee is Updated");
	        	 apiResponse.setData(existingEmployee);
	        }
		  return apiResponse;
	  }
	   //get Project details based on employeeID
	  public APIResponse<Project> get_ProjectByEmployeeId(String employeeId){
		  APIResponse<Project> apiResponse=new APIResponse<>();
		   //first Get the Employee
		  Employee employee=employeeRepo.findById(employeeId).get();
		  if(employee!=null) {
			  if(employee.getProject()!=null) {
				  //now get the Project from the Employee and pass to API
				  Project project=employee.getProject();
				  apiResponse.setStatusCode(HttpStatus.OK.value());
				  apiResponse.setMessage("Employee With ID-->"+employeeId+" is haveing Below Project");
				  apiResponse.setData(project);
				  
			  }else {
				  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
				  apiResponse.setMessage("Employee is On Beanch");
				  return apiResponse;
			  }
			  
		  }else {
			  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());//not found
			  apiResponse.setMessage("Employee do not Exists with this ID-->"+employeeId);
			  return apiResponse;
		  }
		  return apiResponse;
	  }
	  //delete the Employee
	  public APIResponse<Employee> delete_Employee(String employeeId){
		  APIResponse<Employee> apiResponse=new
				  APIResponse<>();
		   Optional<Employee> employeeBYID =employeeRepo.findById(employeeId);
		   if(employeeBYID.isEmpty()) {
			   apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
				  apiResponse.setMessage("Employee Not Found");
				  return apiResponse;
		   }
		   Employee employee=employeeBYID.get();
		   //delete the Employee now from the database
		     employeeRepo.delete(employee);
		     apiResponse.setStatusCode(HttpStatus.ACCEPTED.value());//202
		     apiResponse.setMessage("Employee is Deleted ");
		     apiResponse.setData(employee);//send deleted Employee
		   return apiResponse;
	  }
	  
	  
	  
	  //getting employee which are One Bench
	  
	  
	  
}
