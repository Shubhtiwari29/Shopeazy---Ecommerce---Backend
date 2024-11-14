package com.shopeazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopeazy.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
