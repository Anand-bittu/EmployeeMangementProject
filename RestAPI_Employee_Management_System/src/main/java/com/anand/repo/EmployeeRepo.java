package com.anand.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,String> {

	public Optional<Employee> findByEmail(String email);
	
	
}
