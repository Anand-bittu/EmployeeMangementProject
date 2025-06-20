package com.anand.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.User;

public interface UserRepo extends JpaRepository<User,Long> {

	//get the user by email id
	public Optional<User> findByEmail(String email);
	
}
