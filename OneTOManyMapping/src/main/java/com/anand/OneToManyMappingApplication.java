package com.anand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.anand.entity.Address;
import com.anand.entity.Person;
import com.anand.service.PersonServices;

@SpringBootApplication
public class OneToManyMappingApplication {

	public static void main(String[] args) {
   		SpringApplication.run(OneToManyMappingApplication.class, args);
	      //Person entity
			/*
			 * Person personEntity=new Person(); personEntity.setName("anand");
			 * personEntity.setNumber(45127); //address object List<Address>
			 * list_Address=Arrays.asList(new Address("hyd","Ts", personEntity), new
			 * Address("nodia","delhi", personEntity)); //set the address
			 * personEntity.setAddress(list_Address); PersonServices
			 * personService=ctx.getBean(PersonServices.class); //know save the this person
			 * Object personService.savePerson(personEntity);
			 */
   	 System.out.println("---------");
   	 
		/*
		 * Person personEntity=new Person(); personEntity.setName("bittu");
		 * personEntity.setNumber(3456); //address object List<Address>
		 * list_Address=Arrays.asList(new Address("mumbai","MH", personEntity), new
		 * Address("BarathaPora","Rajashthan", personEntity)); //set the address
		 * personEntity.setAddress(list_Address); PersonServices
		 * personService=ctx.getBean(PersonServices.class); //know save the this person
		 * Object personService.savePerson(personEntity);
		 * 
		 * 
		 */
   	 
		/*
		 * System.out.println("delete the person"); Person person=new Person(); //pid
		 * int pid= person.getPid(); System.out.println(pid); PersonServices
		 * services=ctx.getBean(PersonServices.class); services.deletePerson(pid);
		 * System.out.println("person is deleted");
		 */
   	     
   	         
   	         
	
	}

}
