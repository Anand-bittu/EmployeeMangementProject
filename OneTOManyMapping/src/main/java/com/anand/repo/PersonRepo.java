package com.anand.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.Person;

public interface PersonRepo extends JpaRepository<Person,Integer> {

	
	
}
