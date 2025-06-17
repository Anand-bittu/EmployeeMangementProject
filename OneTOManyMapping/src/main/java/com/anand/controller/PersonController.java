package com.anand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anand.dto.PersonDto;
import com.anand.entity.Person;
import com.anand.service.PersonServices;

@RestController
public class PersonController {

	@Autowired
	private PersonServices personServices;
	
	//save the Person
	@PostMapping("/savePerson")
	public ResponseEntity<Person> save_Person(@RequestBody Person person){
	Person savePerson = personServices.savePerson(person);
		return new ResponseEntity<>(savePerson,HttpStatus.OK);
	}
	//delete the Person
	@DeleteMapping("/delete/{pid}")
	public ResponseEntity<String> delete_Person(@PathVariable ("pid")int pid){
		   personServices.delete_Person(pid);
		   return new ResponseEntity<String>("Person is deleted",HttpStatus.OK);
	}
	
	/*
	 * @PostMapping("/update/{pid}") public ResponseEntity<String> update_Person
	 * (@PathVariable ("pid") int pid,@RequestBody PersonDto personDto){ //call the
	 * services method personServices.update_Person(pid, personDto); return new
	 * ResponseEntity<String>("Person is Updated",HttpStatus.OK); }
	 */
	
	//get the person by id
	@GetMapping("/byid/{pid}")
	public ResponseEntity<Person> get_Person_By_Id(@PathVariable int pid) {
	    Person personByID = personServices.getPersonById(pid);
	    System.out.println("inside responseEntity");
		return new ResponseEntity<Person>(personByID,HttpStatus.OK);
	}

	public void m7() {
		System.out.println("M7 method ");
	}
	public void m8() {
		System.out.println("m8 method");
	}
  
	public void m9() {
		System.out.println("m9 method");
	}
	
	
}
