package com.leavemanagement.LeaveManagement.services;

import com.leavemanagement.LeaveManagement.payload.UserDto;

public interface UserService {
	UserDto createEmployee(UserDto userDto);
	UserDto createManager(UserDto userDto);
}
