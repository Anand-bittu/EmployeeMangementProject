package com.anand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.anand.service.CustomUser;

@Configuration
@EnableWebSecurity //apply web Security for your application in the database
public class SecurityConfig {

	@Autowired
	private CustomUser customUserService;
	
	//password Encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		//these is a BCryptPassWordEncoder
		return new BCryptPasswordEncoder();
	}
	
	//apply SecurityFilter Chain for our application for Http methods
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
	throws Exception{
		System.out.println("inside SecurityFilterChain-----------");
		//first disable csrf  
		http.csrf().disable();
		//now autherizer requests 
		http.authorizeHttpRequests((req)->{
			//allow access to login page
			req
			//.requestMatchers("/login/**").permitAll()// Allow public access to login/register endpoints
			//.requestMatchers("/admin/**").permitAll()  //only admin role can access these
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.requestMatchers("/employee/**").hasRole("EMPLOYEE")// EMPLOYEE-only routes
			.requestMatchers("/client/**").hasRole("CLIENT")
		    .anyRequest().authenticated();// All other requests must be authenticated
		});
		/*This line configures  Enable HTTP Basic authentication (username/password dialog)  which is the standard way 
		 * for users to log in with a username and password via postMan or swager.
		 */
		 http.httpBasic(Customizer.withDefaults());
		 // Build and return the security filter chain
		return http.build();
	}
	
	@Bean
	//create a authoticate Provider
	//it taken two thing one is services and other one is passwordEncoder
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		System.out.println("inside DAO AuthenticationProvider-------");
		//create the daoAuthenticationProvider
		DaoAuthenticationProvider provider=new
				DaoAuthenticationProvider();
		//take service class load findByPersonEmail
		provider.setUserDetailsService(customUserService);
		//take bCrptEncoder
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	//create AuthenticationManger to mange AuthProvider
	@Bean
	public AuthenticationManager authManger(AuthenticationConfiguration cfg) throws Exception{
		System.out.println("inside AuthenticationManger-----");
		return cfg.getAuthenticationManager();
	}
	
}
