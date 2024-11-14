package com.shopeazy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopeazy.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public  User findByEmail(String email);
	
	
}
