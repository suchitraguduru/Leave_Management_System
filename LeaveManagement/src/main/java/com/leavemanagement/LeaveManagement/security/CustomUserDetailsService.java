package com.leavemanagement.LeaveManagement.security;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leavemanagement.LeaveManagement.entity.Role;
import com.leavemanagement.LeaveManagement.entity.Users;
import com.leavemanagement.LeaveManagement.exception.ResourceNotFoundException;
import com.leavemanagement.LeaveManagement.repository.UserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	//method to get all user details by username (email)
	@Override
	public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException{
		try {
			Users user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user ","user email", 0));
			return new User(user.getEmail(),user.getPassword(),getAuthorities(user.getRole()));
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Role role){
		try {
			return Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName()));
		}catch(Exception e) {
			throw e;
		}
		
	}

}
