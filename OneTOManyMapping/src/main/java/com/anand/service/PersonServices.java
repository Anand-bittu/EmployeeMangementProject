package com.anand.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anand.dto.AddressDto;
import com.anand.dto.PersonDto;
import com.anand.entity.Address;
import com.anand.entity.Person;
import com.anand.repo.AddressRepo;
import com.anand.repo.PersonRepo;

@Service
public class PersonServices {
	@Autowired
	private PersonRepo personRepo;
	
	//save person in the database
	public Person savePerson(Person person) {
		//first get the address from the person 
		//this logic show set pid in the database
		List<Address> List_address = person.getAddress();
	     if(List_address!=null) {
	    	 for(Address address:List_address) {
	    		 address.setPerson(person);
	    	 }
	     }
	        // 1. CascadeType.ALL on Person's @OneToMany
	        // 2. The bidirectional link established above in the loop.		
          return personRepo.save(person);		
	}
	
	//delete the person from the database
	public void delete_Person(int pid) {
		int a=10;
		System.out.println("Inside Delete Person");
		personRepo.deleteById(pid);
	}
     
	/*
	 * //update the person object public void update_Person(int pid,PersonDto
	 * personDto) { //get the Person by Id Person existingPerson =
	 * personRepo.findById(pid).get(); // now set the Person first
	 * existingPerson.setName(personDto.getName());
	 * existingPerson.setNumber(personDto.getNumber()); //first get the list of
	 * address List<AddressDto> list_Address= personDto.getListaddress(); //take the
	 * address from the entity List<Address> newAddressList=new ArrayList<>(); //now
	 * code if(list_Address!=null) { for(AddressDto dto:list_Address) { //now create
	 * the address class of entity Address address=new Address();
	 * address.setCity(dto.getCity()); address.setState(dto.getState()); //set the
	 * eixting person address.setPerson(existingPerson); //now add this update
	 * address back to new AddressList newAddressList.add(address); } } //now add
	 * these updated address to the existing person
	 * existingPerson.setAddress(newAddressList); //now save updated person to the
	 * database personRepo.save(existingPerson); }
	 */
	//find the person  by Id
	  public Person getPersonById(int id) {
		Person person=personRepo.findById(id).get();
		return person!=null ? person:null;
	  }
	
	
	
}
