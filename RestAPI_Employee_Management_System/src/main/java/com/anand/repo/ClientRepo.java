package com.anand.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.Client;

public interface ClientRepo  extends JpaRepository<Client,String>{

	
}
