package com.anand.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.ContactDetails;

public interface ContactDetailsRepo  extends JpaRepository<ContactDetails,Long>{

	
	public Optional<ContactDetails> findByEmail(String email);
	
}
