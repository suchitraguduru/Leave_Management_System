package com.leavemanagement.LeaveManagement.serviceImpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leavemanagement.LeaveManagement.controller.ManagerController;
import com.leavemanagement.LeaveManagement.entity.Role;
import com.leavemanagement.LeaveManagement.entity.Users;
import com.leavemanagement.LeaveManagement.payload.UserDto;
import com.leavemanagement.LeaveManagement.repository.UserRepo;
import com.leavemanagement.LeaveManagement.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//Service method to create employee
	@Override
	public UserDto createEmployee(UserDto userDto) {
		logger.info("Entering into employee creation method");
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users user = this.modelMapper.map(userDto, Users.class);
		user.setRole(Role.EMPLOYEE);	
		Users savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser,UserDto.class);
	}
	
	//Service method to create manager
	@Override
	public UserDto createManager(UserDto userDto) {
		logger.info("Entering into manager creation method");
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Users user = this.modelMapper.map(userDto, Users.class);
		user.setRole(Role.MANAGER);
		Users savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser,UserDto.class);
	}

}
