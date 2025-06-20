package com.anand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anand.dto.APIResponse;
import com.anand.entity.Client;
import com.anand.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService client_Service;
	
	//get ClientDetails
	@GetMapping("/getClientInfo")
	public APIResponse<Client> getClientDetils(Authentication authentication){
		//get Current Authentication User  email ID
	     String email=authentication.getName();
		return  client_Service.get_Client_By_Email(email);
	}
	
}
