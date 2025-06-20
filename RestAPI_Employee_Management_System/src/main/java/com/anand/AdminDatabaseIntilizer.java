package com.anand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.anand.entity.RoleType;
import com.anand.entity.User;
import com.anand.repo.UserRepo;

@Component
public class AdminDatabaseIntilizer implements CommandLineRunner {

private Logger logger=LoggerFactory.getLogger(AdminDatabaseIntilizer.class);
	
	
	@Autowired
    private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
     * Initializes the database with a default admin user if no users exist.
     * This method is executed at application startup because the class
     * implements CommandLineRunner and is a Spring-managed bean.
     * @throws Exception if any error occurs during initialization.
     */
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("The Run Method Started ");
		System.out.println("initilze dabatase-------run method-----");
      //default emailId
		String defaultEmailId="admin@gmail.com";
		 // If no user with the default email exists in the database
		if(!userRepo.findByEmail(defaultEmailId).isPresent()) {
			//create a new User Object
			User admin=new User();
			//set the email id
			admin.setEmail(defaultEmailId);
			/* first encode the password before save 
			 * in the database by using passwordEncoder
			 * 
			 */
			admin.setPassword(passwordEncoder.encode("admin123"));
			//now set the role as adminRole
			admin.setRole(RoleType.ADMIN);
			//now save the admin in the database
			userRepo.save(admin);
			System.out.println("default admin is created in the database");
			logger.info("The Run Method Ended Here");
		}	
	}

}
