package com.anand.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anand.entity.RoleType;
import com.anand.entity.User;
import com.anand.repo.UserRepo;

@Service
public class CustomUser implements UserDetailsService {

	
	@Autowired
	private UserRepo userRepo;
	/*
	 * This method is automatically called by Spring Security when a user tries to log in.
     * It uses the provided email to find the user in the database.
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		//first load the user from the database using the provided email
		  User user=userRepo.findByEmail(email)
				  .orElseThrow(()->new UsernameNotFoundException("User do not exists"));
		  /*
	       *  Create a Spring Security User object from the retrieved User entity.
	       *  This object implements the UserDetails interface 
	       *  and is used by Spring Security
	       *   for authentication and authorization.	  
	       */
		  org.springframework.security.core.userdetails.User userSecurity=
        		  new org.springframework.security.core.userdetails
        		  .User(
        				  user.getEmail(),//get the email from User entity class
        				  user.getPassword(),//get password from User entity class
        				  getAuthorities(user.getRole())// A collection of GrantedAuthority representing the user's roles.
        				  );
		return userSecurity;
	}
	// Helper method to convert the application's RoleType to Spring Security's GrantedAuthority.
		private Collection<GrantedAuthority> getAuthorities(RoleType role){
			
			 // Spring Security expects roles to be prefixed with "ROLE_".
			String roleName="ROLE_"+role.name();
			// Create a SimpleGrantedAuthority with the formatted role name and return it in a singleton list.
		  return Collections.singletonList(new SimpleGrantedAuthority(roleName));
		}

}
