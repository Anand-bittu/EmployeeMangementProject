package com.anand.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.Address;

public interface AddressRepo extends JpaRepository<Address,Integer> {

}
