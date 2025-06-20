package com.anand.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anand.dto.APIResponse;
import com.anand.entity.Client;
import com.anand.entity.Employee;
import com.anand.entity.Project;
import com.anand.repo.ClientRepo;
import com.anand.repo.ProjectRepository;

@Service
@Transactional
public class ProjectService {
    
	@Autowired
	private ClientRepo client_Repo;
	
	@Autowired
	private ProjectRepository project_Repo;
	
	//save the Project
	public APIResponse<Project> save_Project(String clientId,Project project){
		APIResponse<Project> responsAPI=new
				APIResponse<>();
		     //first Get Client 
	  Optional<Client> clientById = client_Repo.findById(clientId);
	  // If client is not found
	  if(!clientById.isPresent()) {
		  responsAPI.setStatusCode(HttpStatus.NOT_FOUND.value());//404
		  responsAPI.setMessage("Client Not Found");
		  return responsAPI;//return with error msg
		}
		//now check enter date is Correct or not
	// If project end date is before today
	  if(project.getEndDate().isBefore(LocalDate.now())) {
		  responsAPI.setStatusCode(HttpStatus.CONFLICT.value()); //409
		  responsAPI.setMessage("End date should be after start date");
		  return responsAPI; // return with error msg
	  }
	  //if all good set the client to the project first
	  project.setClient(clientById.get());
	  //now save the Project with this client
	  project_Repo.save(project);
	  responsAPI.setStatusCode(HttpStatus.CREATED.value()); //201
	  responsAPI.setMessage("Project is Inserted sucsefully");
	  responsAPI.setData(project);
		return responsAPI;
	}
	
	//update Project
	public APIResponse<Project> update_Project(String projectId,Project projectResponses){
		//Create the API Response Object
		APIResponse<Project> apiResponse=new
				APIResponse<>();
		 Project existingProject=project_Repo.findById(projectId).get();
		 if(existingProject==null) {
			 apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());//404
			 apiResponse.setMessage("Project do not eixts with this ID-->"+projectId);
			 return apiResponse;
		 }else {
			  if(projectResponses.getName()!=null)
				 existingProject.setName(projectResponses.getName());
			  if(projectResponses.getEndDate()!=null) {
				  if(projectResponses.getEndDate().isBefore(existingProject.getStartDate())) {
					  apiResponse.setStatusCode(HttpStatus.CONFLICT.value());//409
					  apiResponse.setMessage("Project end Date should be after startdate-->"+existingProject.getStartDate());
					  return apiResponse;
				  }else {
					  existingProject.setEndDate(projectResponses.getEndDate());
				  }
			  }
			  //now save these existing project to the database
			  project_Repo.save(existingProject);
			  //succesfull Message 
			  apiResponse.setStatusCode(HttpStatus.OK.value());//200
			  apiResponse.setMessage("Project is Updated ");
			  apiResponse.setData(existingProject);	 
		 }
		return apiResponse;
	}
	//Get All Project
	public APIResponse<List<Project>> get_AllProjects(){
		APIResponse<List<Project>> apiResponse=new
				APIResponse<>();
	  List<Project> listProject=project_Repo.findAll();
	  //now set the Resposne
	  apiResponse.setStatusCode(HttpStatus.OK.value());
	  apiResponse.setMessage("All the Project are shown Below");
	  apiResponse.setData(listProject);
		return apiResponse;
	}
	//get the Project based on Project ID
	public APIResponse<Project> get_ProjectONProjectID(String projectId){
	   APIResponse<Project> apiResponse=new
			   APIResponse<>();
	    Optional<Project> projectByID= project_Repo.findById(projectId);
		if(projectByID.isEmpty()) {
			apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			apiResponse.setMessage("Project not Found");
			return apiResponse;
		}
		//all good
		apiResponse.setStatusCode(HttpStatus.OK.value());
		apiResponse.setMessage("Project Found");
		apiResponse.setData(projectByID.get());		
		return apiResponse;
	}
	//get Client Information based On projectID
	public APIResponse<Client> get_ClientInfoByProjectID(String projectId){
		APIResponse<Client> apiResponse=new APIResponse<>();
	  Optional<Project> projectByID=project_Repo.findById(projectId);
		 if(projectByID.isEmpty()) {
			 apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			 apiResponse.setMessage("Project do not exists with these ID-->"+projectId);
			 return apiResponse;
		 }
		 //now get the client from project
	    Client client=projectByID.get().getClient();
	    apiResponse.setStatusCode(HttpStatus.OK.value());
	    apiResponse.setMessage("Client Found ");
	    apiResponse.setData(client);
		return apiResponse;
	}
	//admin can get all the employee on the basis of project-id
   public APIResponse<List<Employee>> get_ListOFEmployeeBYProjectID(String projectId){
	  APIResponse<List<Employee>> apiResponse=new
			  APIResponse<>();
	      Optional<Project> projectByID=project_Repo.findById(projectId);
	   if(projectByID.isEmpty()) {
		   apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		   apiResponse.setMessage("Project do not exists");
		   return apiResponse;
	   }
	     List<Employee> listOFEmployee=projectByID.get().getEmployee();
	     apiResponse.setStatusCode(HttpStatus.OK.value());
		    apiResponse.setMessage("Employees Found");
		    apiResponse.setData(listOFEmployee);
			return apiResponse;
   }
	//delete the Project first
   public APIResponse<Project> delete_Project(String projectId){
	  APIResponse<Project> apiResponse=new
			  APIResponse<>();
	    Optional<Project> projectByID=project_Repo.findById(projectId);
		   if(projectByID.isEmpty()) {
			   apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			   apiResponse.setMessage("Project do not exists");
			   return apiResponse;
		   }
	        //if Project found delete the Project first 
		   Project project=projectByID.get();
		   //delete the Project first
		   project_Repo.delete(project);
		   apiResponse.setStatusCode(HttpStatus.OK.value());
		   apiResponse.setMessage("Project deleted ");
		   apiResponse.setData(project);//send deleted project	   
	   return apiResponse;
   }
	
}
