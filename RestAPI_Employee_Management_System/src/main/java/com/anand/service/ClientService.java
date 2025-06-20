package com.anand.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anand.dto.APIResponse;
import com.anand.dto.SetPassword;
import com.anand.entity.Client;
import com.anand.entity.ContactDetails;
import com.anand.entity.Project;
import com.anand.entity.RoleType;
import com.anand.entity.User;
import com.anand.repo.ClientRepo;
import com.anand.repo.ContactDetailsRepo;

@Service
@Transactional
public class ClientService {

	@Autowired
	private ClientRepo client_Repo;
	
	@Autowired
	private ContactDetailsRepo contact_Repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//method to save the Client
	public APIResponse<Client> save_Client(Client client){
		APIResponse<Client> apiResponse=
				new APIResponse<>();
		//first get the List of contact details 
		List<ContactDetails> listContactDetails=client.getContactDetails();
		  //check if the Client already exists or not
		if(listContactDetails!=null&& !listContactDetails.isEmpty()) {
			 // Iterate through each contact person to check if their email already exists
		    for(ContactDetails contactDetails:listContactDetails) {
		        if(contact_Repo.findByEmail(contactDetails.getEmail()).isPresent()) {
		        	apiResponse.setStatusCode(HttpStatus.CONFLICT.value());  //409 email already Exists
		            apiResponse.setMessage("Email already Exists!!!"+contactDetails.getEmail());
		            apiResponse.setData(null);
		            //return apiResponse 
		            return apiResponse;
		        }
		    }
		    // If all email are unique, link each contact person to the client
		    for(ContactDetails contactDetails:listContactDetails) {
		    	contactDetails.setClient(client);
		    }   
		}
		 // Save the client entity, which will also save contact persons due to cascading
		   client_Repo.save(client);
		   apiResponse.setStatusCode(HttpStatus.CREATED.value());//201
		   apiResponse.setMessage("Client Inserted succesfully!!");
		   apiResponse.setData(client);
		return apiResponse;
	}
	//method to get the Client by ClientId
	public APIResponse<Client> get_ClientBYClientID(String clientId){
		APIResponse<Client> res=new APIResponse<>();
		//now get the Client by ClientID
		 Optional<Client> client= client_Repo.findById(clientId);
		 if(client.isPresent()) {
			 res.setStatusCode(HttpStatus.OK.value());  //200
			 res.setMessage("Client Found By these ID::"+clientId);
			 res.setData(client.get());
		 }else {
			 res.setStatusCode(HttpStatus.NOT_FOUND.value()); //404
			 res.setMessage("Client do not Exists");
			 res.setData(null);
		 }
		return res;
	}
	//method to get list of Client
	public APIResponse<List<Client>> get_ListOFEmployee(){
		APIResponse<List<Client>> responseAPI=
				new APIResponse<>();
		List<Client> list_Client = client_Repo.findAll();
		if(list_Client.isEmpty()) {
			responseAPI.setStatusCode(HttpStatus.NOT_FOUND.value());//404
			responseAPI.setMessage("No Client avaible");
			responseAPI.setData(null);
		}else {
			responseAPI.setStatusCode(HttpStatus.OK.value());//200
			responseAPI.setMessage("Client are avaiable");
			responseAPI.setData(list_Client);
		}
		return responseAPI;
	}
	
	//set the Client Password
	 
	public APIResponse<Client> setPassword(SetPassword passwordRequest){
		APIResponse<Client> apiResponse=new APIResponse<>();
		//get the Client from the client ID
		  Optional<Client> clientByID=client_Repo.findById(passwordRequest.getId());
		  if(clientByID.isEmpty()) {
			  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			  apiResponse.setMessage("Client not Found");
			  return apiResponse;
		  }
		  //if all good get Client 
		    Client client=clientByID.get();
		    //set Commen Password for each client
		    // Loop through every contact person associated with this client.
		    for(ContactDetails contactDetails:client.getContactDetails()) {
		    	// Get the user account linked to the current contact person.
		    	     User user=contactDetails.getUser();
		    	     // If this contact person doesn't have a user account yet:
		    	     if(user==null) {
		    	    	 //create a New User
		    	    	 user=new User();
		    	    	/*
		    	    	 *  Set the user's email from the contact person's email (often used as username).
                            user.setEmail(contactdetails.getEmail());
		    	    	 */
		    	    	 user.setEmail(contactDetails.getEmail());
		    	    	 // Assign the "CLIENT" role to this new user.
		    	    	 user.setRole(RoleType.CLIENT);
		    	     }
		    	     /*Hash the new password from the request for security.
                          This is done for both new and existing user accounts.
		    	      */
		    	     user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
		    	     // Set this user back to contact person and save
		    	     contactDetails.setUser(user);
		    	     //now save these contactDetails to the database
		    	     contact_Repo.save(contactDetails);	     
		    }
		    //succesfull message
		    apiResponse.setStatusCode(HttpStatus.OK.value());
		    apiResponse.setMessage("Succesfully set password to the Client"+passwordRequest.getId());
		    apiResponse.setData(client);
		return apiResponse;
	}
	//add the Contact details to the existing client By ClientId
	public APIResponse<ContactDetails> add_Contact(String clientId,ContactDetails contactDetails){
		APIResponse<ContactDetails> apiResponse=new
				APIResponse<>();
		//first Get the Client By ClientId
		Optional<Client> clientBYId=client_Repo.findById(clientId);
		if(clientBYId.isEmpty()) {
			apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());//404
			apiResponse.setMessage("Client do not exists with this ID-->"+clientId);
			return apiResponse;
		}
		//no check if the Email already exists
		if(contact_Repo.findByEmail(contactDetails.getEmail()).isPresent()) {
			apiResponse.setStatusCode(HttpStatus.CONFLICT.value()); //409
			apiResponse.setMessage("Email already exists-->"+contactDetails.getEmail());
			return apiResponse;
		}
		//if all good  Set reference to parent client
		contactDetails.setClient(clientBYId.get());
		//now save these ContactDetail back to Database
		contact_Repo.save(contactDetails);
		//now succesfull messages
		apiResponse.setStatusCode(HttpStatus.CREATED.value());
		apiResponse.setMessage("Contact Saved Successfully to clientId::"+clientId);
		apiResponse.setData(contactDetails);
		return apiResponse;
	}
	
	//get client by Email
	public APIResponse<Client> get_Client_By_Email(String email){
		APIResponse<Client> apiResponse=new
				APIResponse<>();
		 Client client=contact_Repo.findByEmail(email).get().getClient();
		 if(client==null) {
			 apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			 apiResponse.setMessage("Client Do not eixts -->"+email);
			 return apiResponse;
		 }
		 apiResponse.setStatusCode(HttpStatus.OK.value());//200
		 apiResponse.setMessage("Client details-->");
		 apiResponse.setData(client);
		return apiResponse;
	}
	/*
	 *  admin can get the project details from the client-id.
	 *   [a client can have more then one project.]
	 */
	public APIResponse<List<Project>> get_ListOFProjectBYClientID(String clientId){
		APIResponse<List<Project>> apiResponse=
				new APIResponse<>();
	  Optional<Client> clientBYID=client_Repo.findById(clientId);
	  if(clientBYID.isEmpty()) {
		  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		  apiResponse.setMessage("Client not Found By this ID-->"+clientId);
		  return apiResponse;
	  }
	     List<Project> listProjects=clientBYID.get().getProjects();
	     apiResponse.setStatusCode(HttpStatus.OK.value());
	     apiResponse.setMessage("List of Project associated with this clientID-->"+clientId);
	     apiResponse.setData(listProjects);
		return apiResponse;
	}
	//delete the Client first 
	public APIResponse<Client> delete_Client(String clientId){
		APIResponse<Client> apiResponse=new
			             APIResponse<>();
	  Optional<Client> clientBYId=client_Repo.findById(clientId);
	  if(clientBYId.isEmpty()) {
		  apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		  apiResponse.setMessage("Client not Found By this ID-->"+clientId);
		  return apiResponse;
	  }
	      Client client = clientBYId.get();
	      //now delete the Client
	      client_Repo.delete(client);
	      apiResponse.setStatusCode(HttpStatus.OK.value());
	      apiResponse.setMessage("client deleted ");
	      apiResponse.setData(client);//send deleted client 
		return apiResponse;
	}
	
}
