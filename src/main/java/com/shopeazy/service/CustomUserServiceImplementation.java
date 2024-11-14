package com.shopeazy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopeazy.model.User;
import com.shopeazy.repository.UserRepository;

/*NOTE*-> SINCE WE HAVE SECURED ALL THE API AFTER 8080/API, IT BY DEFAULT OPENS THE DEFAULT SPRING SECURITY LOGIN PAGE
 AND WE WILL AHVE TO USE SPRING SECURITY PASSWORD TO LOGIN, BUT HERE WE WANT TO LOGIN USING USER'S DETAILS SUCH AS EMAIL AND PASSWORD
 FRO THIS REASON WE ARE IMPLEMENTING THIS CLASS*/
@Service
public class CustomUserServiceImplementation implements UserDetailsService {
	
	
	private UserRepository userRepository;
	
	public CustomUserServiceImplementation(UserRepository userRepository) {
		this.userRepository  = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
		    throw new UsernameNotFoundException("User Not Found with Email : " + username);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}
	

}
