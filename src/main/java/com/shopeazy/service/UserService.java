package com.shopeazy.service;

import java.util.List;

import com.shopeazy.exception.UserException;
import com.shopeazy.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();
}
