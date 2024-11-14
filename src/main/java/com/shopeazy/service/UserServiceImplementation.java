package com.shopeazy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopeazy.config.JwtProvider;
import com.shopeazy.exception.UserException;
import com.shopeazy.model.User;
import com.shopeazy.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private JwtProvider jwtProvider;
		
	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User>user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User Not Found With User ID: "+ userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user  = userRepository.findByEmail(email);
		if(user==null) {
			throw new UserException("User Not Found With User Emial: "+ email);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
